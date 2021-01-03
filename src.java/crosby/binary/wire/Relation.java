// Code generated by Wire protocol buffer compiler, do not edit.
// Source: OSMPBF.Relation in osmformat.proto
package crosby.binary.wire;

import com.squareup.wire.EnumAdapter;
import com.squareup.wire.FieldEncoding;
import com.squareup.wire.Message;
import com.squareup.wire.ProtoAdapter;
import com.squareup.wire.ProtoReader;
import com.squareup.wire.ProtoWriter;
import com.squareup.wire.Syntax;
import com.squareup.wire.WireEnum;
import com.squareup.wire.WireField;
import com.squareup.wire.internal.Internal;
import java.io.IOException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.util.List;
import okio.ByteString;

public final class Relation extends Message<Relation, Relation.Builder> {
  public static final ProtoAdapter<Relation> ADAPTER = new ProtoAdapter_Relation();

  private static final long serialVersionUID = 0L;

  public static final Long DEFAULT_ID = 0L;

  @WireField(
      tag = 1,
      adapter = "com.squareup.wire.ProtoAdapter#INT64",
      label = WireField.Label.REQUIRED
  )
  public final Long id;

  /**
   * Parallel arrays.
   */
  @WireField(
      tag = 2,
      adapter = "com.squareup.wire.ProtoAdapter#UINT32",
      label = WireField.Label.PACKED
  )
  public final List<Integer> keys;

  @WireField(
      tag = 3,
      adapter = "com.squareup.wire.ProtoAdapter#UINT32",
      label = WireField.Label.PACKED
  )
  public final List<Integer> vals;

  @WireField(
      tag = 4,
      adapter = "crosby.binary.wire.Info#ADAPTER"
  )
  public final Info info;

  /**
   * Parallel arrays
   * This should have been defined as uint32 for consistency, but it is now too late to change it
   */
  @WireField(
      tag = 8,
      adapter = "com.squareup.wire.ProtoAdapter#INT32",
      label = WireField.Label.PACKED
  )
  public final List<Integer> roles_sid;

  /**
   * DELTA encoded
   */
  @WireField(
      tag = 9,
      adapter = "com.squareup.wire.ProtoAdapter#SINT64",
      label = WireField.Label.PACKED
  )
  public final List<Long> memids;

  @WireField(
      tag = 10,
      adapter = "crosby.binary.wire.Relation$MemberType#ADAPTER",
      label = WireField.Label.PACKED
  )
  public final List<MemberType> types;

  public Relation(Long id, List<Integer> keys, List<Integer> vals, Info info,
      List<Integer> roles_sid, List<Long> memids, List<MemberType> types) {
    this(id, keys, vals, info, roles_sid, memids, types, ByteString.EMPTY);
  }

  public Relation(Long id, List<Integer> keys, List<Integer> vals, Info info,
      List<Integer> roles_sid, List<Long> memids, List<MemberType> types,
      ByteString unknownFields) {
    super(ADAPTER, unknownFields);
    this.id = id;
    this.keys = Internal.immutableCopyOf("keys", keys);
    this.vals = Internal.immutableCopyOf("vals", vals);
    this.info = info;
    this.roles_sid = Internal.immutableCopyOf("roles_sid", roles_sid);
    this.memids = Internal.immutableCopyOf("memids", memids);
    this.types = Internal.immutableCopyOf("types", types);
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.id = id;
    builder.keys = Internal.copyOf(keys);
    builder.vals = Internal.copyOf(vals);
    builder.info = info;
    builder.roles_sid = Internal.copyOf(roles_sid);
    builder.memids = Internal.copyOf(memids);
    builder.types = Internal.copyOf(types);
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof Relation)) return false;
    Relation o = (Relation) other;
    return unknownFields().equals(o.unknownFields())
        && id.equals(o.id)
        && keys.equals(o.keys)
        && vals.equals(o.vals)
        && Internal.equals(info, o.info)
        && roles_sid.equals(o.roles_sid)
        && memids.equals(o.memids)
        && types.equals(o.types);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode;
    if (result == 0) {
      result = unknownFields().hashCode();
      result = result * 37 + id.hashCode();
      result = result * 37 + keys.hashCode();
      result = result * 37 + vals.hashCode();
      result = result * 37 + (info != null ? info.hashCode() : 0);
      result = result * 37 + roles_sid.hashCode();
      result = result * 37 + memids.hashCode();
      result = result * 37 + types.hashCode();
      super.hashCode = result;
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(", id=").append(id);
    if (!keys.isEmpty()) builder.append(", keys=").append(keys);
    if (!vals.isEmpty()) builder.append(", vals=").append(vals);
    if (info != null) builder.append(", info=").append(info);
    if (!roles_sid.isEmpty()) builder.append(", roles_sid=").append(roles_sid);
    if (!memids.isEmpty()) builder.append(", memids=").append(memids);
    if (!types.isEmpty()) builder.append(", types=").append(types);
    return builder.replace(0, 2, "Relation{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<Relation, Builder> {
    public Long id;

    public List<Integer> keys;

    public List<Integer> vals;

    public Info info;

    public List<Integer> roles_sid;

    public List<Long> memids;

    public List<MemberType> types;

    public Builder() {
      keys = Internal.newMutableList();
      vals = Internal.newMutableList();
      roles_sid = Internal.newMutableList();
      memids = Internal.newMutableList();
      types = Internal.newMutableList();
    }

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    /**
     * Parallel arrays.
     */
    public Builder keys(List<Integer> keys) {
      Internal.checkElementsNotNull(keys);
      this.keys = keys;
      return this;
    }

    public Builder vals(List<Integer> vals) {
      Internal.checkElementsNotNull(vals);
      this.vals = vals;
      return this;
    }

    public Builder info(Info info) {
      this.info = info;
      return this;
    }

    /**
     * Parallel arrays
     * This should have been defined as uint32 for consistency, but it is now too late to change it
     */
    public Builder roles_sid(List<Integer> roles_sid) {
      Internal.checkElementsNotNull(roles_sid);
      this.roles_sid = roles_sid;
      return this;
    }

    /**
     * DELTA encoded
     */
    public Builder memids(List<Long> memids) {
      Internal.checkElementsNotNull(memids);
      this.memids = memids;
      return this;
    }

    public Builder types(List<MemberType> types) {
      Internal.checkElementsNotNull(types);
      this.types = types;
      return this;
    }

    @Override
    public Relation build() {
      if (id == null) {
        throw Internal.missingRequiredFields(id, "id");
      }
      return new Relation(id, keys, vals, info, roles_sid, memids, types, super.buildUnknownFields());
    }
  }

  public enum MemberType implements WireEnum {
    NODE(0),

    WAY(1),

    RELATION(2);

    public static final ProtoAdapter<MemberType> ADAPTER = new ProtoAdapter_MemberType();

    private final int value;

    MemberType(int value) {
      this.value = value;
    }

    /**
     * Return the constant for {@code value} or null.
     */
    public static MemberType fromValue(int value) {
      switch (value) {
        case 0: return NODE;
        case 1: return WAY;
        case 2: return RELATION;
        default: return null;
      }
    }

    @Override
    public int getValue() {
      return value;
    }

    private static final class ProtoAdapter_MemberType extends EnumAdapter<MemberType> {
      ProtoAdapter_MemberType() {
        super(MemberType.class, Syntax.PROTO_2, MemberType.NODE);
      }

      @Override
      protected MemberType fromValue(int value) {
        return MemberType.fromValue(value);
      }
    }
  }

  private static final class ProtoAdapter_Relation extends ProtoAdapter<Relation> {
    public ProtoAdapter_Relation() {
      super(FieldEncoding.LENGTH_DELIMITED, Relation.class, "type.googleapis.com/OSMPBF.Relation", Syntax.PROTO_2, null);
    }

    @Override
    public int encodedSize(Relation value) {
      int result = 0;
      result += ProtoAdapter.INT64.encodedSizeWithTag(1, value.id);
      result += ProtoAdapter.UINT32.asPacked().encodedSizeWithTag(2, value.keys);
      result += ProtoAdapter.UINT32.asPacked().encodedSizeWithTag(3, value.vals);
      result += Info.ADAPTER.encodedSizeWithTag(4, value.info);
      result += ProtoAdapter.INT32.asPacked().encodedSizeWithTag(8, value.roles_sid);
      result += ProtoAdapter.SINT64.asPacked().encodedSizeWithTag(9, value.memids);
      result += MemberType.ADAPTER.asPacked().encodedSizeWithTag(10, value.types);
      result += value.unknownFields().size();
      return result;
    }

    @Override
    public void encode(ProtoWriter writer, Relation value) throws IOException {
      ProtoAdapter.INT64.encodeWithTag(writer, 1, value.id);
      ProtoAdapter.UINT32.asPacked().encodeWithTag(writer, 2, value.keys);
      ProtoAdapter.UINT32.asPacked().encodeWithTag(writer, 3, value.vals);
      Info.ADAPTER.encodeWithTag(writer, 4, value.info);
      ProtoAdapter.INT32.asPacked().encodeWithTag(writer, 8, value.roles_sid);
      ProtoAdapter.SINT64.asPacked().encodeWithTag(writer, 9, value.memids);
      MemberType.ADAPTER.asPacked().encodeWithTag(writer, 10, value.types);
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public Relation decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          case 1: builder.id(ProtoAdapter.INT64.decode(reader)); break;
          case 2: builder.keys.add(ProtoAdapter.UINT32.decode(reader)); break;
          case 3: builder.vals.add(ProtoAdapter.UINT32.decode(reader)); break;
          case 4: builder.info(Info.ADAPTER.decode(reader)); break;
          case 8: builder.roles_sid.add(ProtoAdapter.INT32.decode(reader)); break;
          case 9: builder.memids.add(ProtoAdapter.SINT64.decode(reader)); break;
          case 10: {
            try {
              builder.types.add(MemberType.ADAPTER.decode(reader));
            } catch (ProtoAdapter.EnumConstantNotFoundException e) {
              builder.addUnknownField(tag, FieldEncoding.VARINT, (long) e.value);
            }
            break;
          }
          default: {
            reader.readUnknownField(tag);
          }
        }
      }
      builder.addUnknownFields(reader.endMessageAndGetUnknownFields(token));
      return builder.build();
    }

    @Override
    public Relation redact(Relation value) {
      Builder builder = value.newBuilder();
      if (builder.info != null) builder.info = Info.ADAPTER.redact(builder.info);
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}
