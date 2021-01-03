/**
 * Copyright (c) 2010 Scott A. Crosby. <scott@sacrosby.com>
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package crosby.binary;

import crosby.binary.file.BlockOutputStream;
import crosby.binary.file.FileBlock;
import crosby.binary.wire.DenseNodes;
import crosby.binary.wire.HeaderBlock;
import crosby.binary.wire.Info;
import crosby.binary.wire.Node;
import crosby.binary.wire.PrimitiveBlock;
import crosby.binary.wire.PrimitiveGroup;
import crosby.binary.wire.Relation.MemberType;
import crosby.binary.wire.Relation;
import crosby.binary.wire.StringTable;
import crosby.binary.wire.Way;
import okio.ByteString;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

public class WriteFileTest {

    @Test
    public void testSimpleFileBlock1() throws Exception {
        test("deflate", "/SimpleFileBlock1-deflate.osm.pbf");
        test("none", "/SimpleFileBlock1-none.osm.pbf");
    }

    @Test
    public void testGranFileBlock1() throws Exception {
        test("deflate", "/GranFileBlock1-deflate.osm.pbf");
        test("none", "/GranFileBlock1-none.osm.pbf");
    }

    private void test(String compress, String resource) throws IOException, URISyntaxException {
        try (ByteArrayOutputStream bytes = new ByteArrayOutputStream()) {
            BuildTestFile builder = new BuildTestFile(new BlockOutputStream(bytes), compress);
            if (resource.startsWith("/SimpleFileBlock1")) {
                builder.makeSimpleFileBlock1();
            } else if (resource.startsWith("/GranFileBlock1")) {
                builder.makeGranFileBlock1();
            } else {
                throw new IllegalArgumentException();
            }
            byte[] expected = Files.readAllBytes(Paths.get(WriteFileTest.class.getResource(resource).toURI()));
            Assert.assertArrayEquals(expected, bytes.toByteArray());
        }
    }
}

class BuildTestFile {
    private final BlockOutputStream output;
    public static final long BILLION = 1000000000L;

    StringTable makeStringTable(String prefix) {
        crosby.binary.wire.StringTable.Builder builder = new crosby.binary.wire.StringTable.Builder();
        builder.s.add(ByteString.encodeUtf8("")); // Never used.
        builder.s.add(ByteString.encodeUtf8(prefix + "Offset1"));
        builder.s.add(ByteString.encodeUtf8(prefix + "Offset2"));
        builder.s.add(ByteString.encodeUtf8(prefix + "Offset3"));
        builder.s.add(ByteString.encodeUtf8(prefix + "Offset4"));
        builder.s.add(ByteString.encodeUtf8(prefix + "Offset5"));
        builder.s.add(ByteString.encodeUtf8(prefix + "Offset6"));
        builder.s.add(ByteString.encodeUtf8(prefix + "Offset7"));
        builder.s.add(ByteString.encodeUtf8(prefix + "Offset8"));
        return builder.build();
    }

    private static Node.Builder node(long id, long lat, long lon) {
        return new Node.Builder().id(id).lat(lat).lon(lon);
    }

    private static Way.Builder way(long id, long... refs) {
        Way.Builder builder = new Way.Builder().id(id);
        for (long ref : refs) {
            builder.refs.add(ref);
        }
        return builder;
    }

    void makeSimpleFileBlock1() throws IOException {
        PrimitiveBlock.Builder b1 = new PrimitiveBlock.Builder();
        b1.stringtable(makeStringTable("B1"));

        b1.primitivegroup.add(
                new PrimitiveGroup.Builder().nodes(Arrays.asList(
                        node(101, 13 * 10 * 1000 * 1000, -14 * 10 * 1000 * 1000)
                                .keys(Collections.singletonList(1)).vals(Collections.singletonList(2)).build(),
                        node(101, 12345678, -23456789).build()) // Should be 1.2345678 degrees lat and -2.3456789 lon.
                ).build()
        );
        b1.primitivegroup.add(
                new PrimitiveGroup.Builder().ways(Arrays.asList(
                        way(201, 101, 1, -1, 10, -20) // Delta coded. Should be 101, 102, 101, 111, 91.
                                .keys(Arrays.asList(2, 3)).vals(Arrays.asList(1, 4)).build(),
                        way(-301, 211, 1, -1, 10, -300) // Delta coded. Should be 211, 212, 211, 221, -79
                                .keys(Arrays.asList(4, 5)).vals(Arrays.asList(3, 6)).build(),
                        way(401, 211, 1).build(),
                        way(501).build())
                ).build()
        );

        b1.primitivegroup.add(
                new PrimitiveGroup.Builder().relations(Arrays.asList(
                        new Relation.Builder()
                                .id(601L)
                                .types(Arrays.asList(MemberType.NODE, MemberType.NODE, MemberType.WAY, MemberType.RELATION))
                                .memids(Arrays.asList(50L, 3L, 3L, 3L))
                                .roles_sid(Arrays.asList(2, 3, 4, 5))
                                .build(),
                        new Relation.Builder()
                                .id(701L)
                                .types(Arrays.asList(MemberType.RELATION, MemberType.RELATION))
                                .memids(Arrays.asList(60L, 5L))
                                .roles_sid(Arrays.asList(6, 7))
                                .build()
                )).build()
        );

        b1.primitivegroup.add(
                new PrimitiveGroup.Builder()
                        .dense(new DenseNodes.Builder()
                                .id(Arrays.asList(1001L, 110L, -2000L, 8889L))
                                .lat(Arrays.asList(12 * 10000000L, 1500000L, -12 * 10000000L, -12 * 10000000L))
                                .lon(Arrays.asList(-12 * 10000000L, 2500000L, 13 * 10000000L, 2 * 10000000L))
                                .keys_vals(Arrays.asList(1, 2, 0, 0, 2, 3, 4, 5, 0, 3, 3, 0))
                                .build()
                        ).build()
        );

        output.write(FileBlock.newInstance("OSMData", b1.build().encodeByteString(), null));

        PrimitiveBlock.Builder b2 = new PrimitiveBlock.Builder();
        b2.lat_offset(10 * BILLION + 109208300)
                .lon_offset(20 * BILLION + 901802700)
                .granularity(1200);
        b2.stringtable(makeStringTable("B2"));

        // Test out granularity stuff.
        b2.primitivegroup.add(
                new PrimitiveGroup.Builder().nodes(Arrays.asList(
                        node(100000L, 0L, 0L).build(),
                        node(100001L, 1000L, 2000L).build(),
                        node(100002L, 1001L, 2001L).build(),
                        node(100003L, 1002L, 2002L).build(),
                        node(100004L, 1003L, 2003L).build(),
                        node(100005L, 1004L, 2004L).build())
                ).build()
        );


        output.write(FileBlock.newInstance("OSMData", b2.build().encodeByteString(), null));
    }


    BuildTestFile(BlockOutputStream output, String compress) throws IOException {
        this.output = output;
        this.output.setCompress(compress);
        HeaderBlock.Builder b = new HeaderBlock.Builder();
        b.required_features(Arrays.asList("OsmSchema-V0.6", "DenseNodes")).source("QuickBrownFox");
        this.output.write(FileBlock.newInstance("OSMHeader", b.build().encodeByteString(), null));
    }

    void makeGranFileBlock1() throws IOException {
        PrimitiveBlock.Builder b1 = new PrimitiveBlock.Builder();
        b1.lat_offset(10 * BILLION + 109208300)
                .lon_offset(20 * BILLION + 901802700)
                .granularity(1200)
                .date_granularity(2500);
        b1.stringtable(makeStringTable("C1"));

        b1.primitivegroup.add(
                new PrimitiveGroup.Builder().nodes(Arrays.asList(
                        node(100001, 1000, 2000)
                                .info(new Info.Builder()
                                        .timestamp(1001L)
                                        .changeset(-12L)
                                        .uid(21)
                                        .user_sid(6)
                                        .build())
                                .build(),
                        node(100002, 1001, 2001)
                                .info(new Info.Builder()
                                        .version(102)
                                        .timestamp(1002L)
                                        .changeset(12L)
                                        .uid(-21)
                                        .user_sid(5)
                                        .build())
                                .build(),
                        node(100003, 1003, 2003)
                                .info(new Info.Builder()
                                        .version(103)
                                        .user_sid(4)
                                        .build())
                                .build())
                ).build()
        );

        // The same, but with different granularities.
        PrimitiveBlock.Builder b2 = new PrimitiveBlock.Builder();
        b2.lat_offset(12 * BILLION + 303)
                .lon_offset(22 * BILLION + 404)
                .granularity(1401)
                .date_granularity(3003);
        b2.stringtable(makeStringTable("C2"));
        b2.primitivegroup.add(
                new PrimitiveGroup.Builder().nodes(Arrays.asList(
                        node(100001, 1000, 2000)
                                .keys(Arrays.asList(1, 1, 3))
                                .vals(Arrays.asList(2, 3, 4)) // Support multiple vals for a key.
                                .build(),
                        node(100002, 1001, 2001).build(),
                        node(100003, 1003, 2003)
                                .keys(Collections.singletonList(5)).vals(Collections.singletonList(6))
                                .build())
                ).build()
        );
        output.write(FileBlock.newInstance("OSMData", b1.build().encodeByteString(), null));
        output.write(FileBlock.newInstance("OSMData", b2.build().encodeByteString(), null));
    }

}
