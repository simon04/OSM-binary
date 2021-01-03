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


import crosby.binary.file.BlockReaderAdapter;
import crosby.binary.file.FileBlock;
import crosby.binary.file.FileBlockPosition;
import crosby.binary.wire.DenseNodes;
import crosby.binary.wire.HeaderBlock;
import crosby.binary.wire.Info;
import crosby.binary.wire.Node;
import crosby.binary.wire.PrimitiveBlock;
import crosby.binary.wire.PrimitiveGroup;
import crosby.binary.wire.Relation;
import crosby.binary.wire.StringTable;
import crosby.binary.wire.Way;
import okio.ByteString;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Date;
import java.util.List;

public abstract class BinaryParser implements BlockReaderAdapter {
    protected int granularity;
    private long lat_offset;
    private long lon_offset;
    protected int date_granularity;
    private String[] strings;

    protected Date getDate(Info info) {
        if (info.timestamp != null) {
            return new Date(date_granularity * info.timestamp);
        } else
            return NODATE;
    }

    public static final Date NODATE = new Date(-1);

    /** Get a string based on the index used.
     * 
     * Index 0 is reserved to use as a delimiter, therefore, index 1 corresponds to the first string in the table 
     * @param id the index
     * @return the string at the given index
     */
    protected String getStringById(int id) {
        return strings[id];
    }

    @Override
    public void handleBlock(FileBlock message) {
        try {
            if (message.getType().equals("OSMHeader")) {
                HeaderBlock headerblock = HeaderBlock.ADAPTER.decode(message.getData());
                parse(headerblock);
            } else if (message.getType().equals("OSMData")) {
                PrimitiveBlock primblock = PrimitiveBlock.ADAPTER.decode(message.getData());
                parse(primblock);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }


    @Override
    public boolean skipBlock(FileBlockPosition block) {
        // System.out.println("Seeing block of type: "+block.getType());
        if (block.getType().equals("OSMData"))
            return false;
        if (block.getType().equals("OSMHeader"))
            return false;
        System.out.println("Skipped block of type: " + block.getType());
        return true;
    }


    /**
     * Convert a latitude value stored in a protobuf into a double, compensating for granularity and latitude offset
     */
    public double parseLat(long degree) {
        // Support non-zero offsets. (We don't currently generate them)
        return (granularity * degree + lat_offset) * .000000001;
    }

    /**
     * Convert a longitude value stored in a protobuf into a double, compensating for granularity and longitude offset
     */
    public double parseLon(long degree) {
        // Support non-zero offsets. (We don't currently generate them)
        return (granularity * degree + lon_offset) * .000000001;
    }

    /**
     * Parse a Primitive block (containing a string table, other paramaters, and PrimitiveGroups
     */
    public void parse(PrimitiveBlock block) {
        StringTable stablemessage = block.stringtable;
        strings = stablemessage.s.stream().map(ByteString::utf8).toArray(String[]::new);

        granularity = block.granularity != null ? block.granularity : PrimitiveBlock.DEFAULT_GRANULARITY;
        lat_offset = block.lat_offset != null ? block.lat_offset : PrimitiveBlock.DEFAULT_LAT_OFFSET;
        lon_offset = block.lon_offset != null ? block.lon_offset : PrimitiveBlock.DEFAULT_LON_OFFSET;
        date_granularity = block.date_granularity != null ? block.date_granularity : PrimitiveBlock.DEFAULT_DATE_GRANULARITY;

        for (PrimitiveGroup groupmessage : block.primitivegroup) {
            // Exactly one of these should trigger on each loop.
            parseNodes(groupmessage.nodes);
            parseWays(groupmessage.ways);
            parseRelations(groupmessage.relations);
            if (groupmessage.dense != null)
                parseDense(groupmessage.dense);
        }
    }

    /**
     * Parse a list of Relation protocol buffers and send the resulting relations to a sink.
     */
    protected abstract void parseRelations(List<Relation> rels);

    /**
     * Parse a DenseNode protocol buffer and send the resulting nodes to a sink.
     */
    protected abstract void parseDense(DenseNodes nodes);

    /**
     * Parse a list of Node protocol buffers and send the resulting nodes to a sink.
     */
    protected abstract void parseNodes(List<Node> nodes);

    /**
     * Parse a list of Way protocol buffers and send the resulting ways to a sink.
     */
    protected abstract void parseWays(List<Way> ways);

    /**
     * Parse a header message.
     */
    protected abstract void parse(HeaderBlock header);

}