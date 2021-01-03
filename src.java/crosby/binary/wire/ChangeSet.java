// Code generated by Wire protocol buffer compiler, do not edit.
// Source: OSMPBF.ChangeSet in osmformat.proto
package crosby.binary.wire;

import com.squareup.wire.FieldEncoding;
import com.squareup.wire.Message;
import com.squareup.wire.ProtoAdapter;
import com.squareup.wire.ProtoReader;
import com.squareup.wire.ProtoWriter;
import com.squareup.wire.Syntax;
import com.squareup.wire.WireField;
import com.squareup.wire.internal.Internal;
import java.io.IOException;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import okio.ByteString;

/**
 * This is kept for backwards compatibility but not used anywhere.
 */
public final class ChangeSet extends Message<ChangeSet, ChangeSet.Builder> {
  public static final ProtoAdapter<ChangeSet> ADAPTER = new ProtoAdapter_ChangeSet();

  private static final long serialVersionUID = 0L;

  public static final Long DEFAULT_ID = 0L;

  @WireField(
      tag = 1,
      adapter = "com.squareup.wire.ProtoAdapter#INT64",
      label = WireField.Label.REQUIRED
  )
  public final Long id;

  public ChangeSet(Long id) {
    this(id, ByteString.EMPTY);
  }

  public ChangeSet(Long id, ByteString unknownFields) {
    super(ADAPTER, unknownFields);
    this.id = id;
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.id = id;
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof ChangeSet)) return false;
    ChangeSet o = (ChangeSet) other;
    return unknownFields().equals(o.unknownFields())
        && id.equals(o.id);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode;
    if (result == 0) {
      result = unknownFields().hashCode();
      result = result * 37 + id.hashCode();
      super.hashCode = result;
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(", id=").append(id);
    return builder.replace(0, 2, "ChangeSet{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<ChangeSet, Builder> {
    public Long id;

    public Builder() {
    }

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    @Override
    public ChangeSet build() {
      if (id == null) {
        throw Internal.missingRequiredFields(id, "id");
      }
      return new ChangeSet(id, super.buildUnknownFields());
    }
  }

  private static final class ProtoAdapter_ChangeSet extends ProtoAdapter<ChangeSet> {
    public ProtoAdapter_ChangeSet() {
      super(FieldEncoding.LENGTH_DELIMITED, ChangeSet.class, "type.googleapis.com/OSMPBF.ChangeSet", Syntax.PROTO_2, null);
    }

    @Override
    public int encodedSize(ChangeSet value) {
      int result = 0;
      result += ProtoAdapter.INT64.encodedSizeWithTag(1, value.id);
      result += value.unknownFields().size();
      return result;
    }

    @Override
    public void encode(ProtoWriter writer, ChangeSet value) throws IOException {
      ProtoAdapter.INT64.encodeWithTag(writer, 1, value.id);
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public ChangeSet decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          case 1: builder.id(ProtoAdapter.INT64.decode(reader)); break;
          default: {
            reader.readUnknownField(tag);
          }
        }
      }
      builder.addUnknownFields(reader.endMessageAndGetUnknownFields(token));
      return builder.build();
    }

    @Override
    public ChangeSet redact(ChangeSet value) {
      Builder builder = value.newBuilder();
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}
