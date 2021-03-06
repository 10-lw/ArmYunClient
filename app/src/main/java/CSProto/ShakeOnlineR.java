// automatically generated by the FlatBuffers compiler, do not modify

package CSProto;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class ShakeOnlineR extends Table {
  public static ShakeOnlineR getRootAsShakeOnlineR(ByteBuffer _bb) { return getRootAsShakeOnlineR(_bb, new ShakeOnlineR()); }
  public static ShakeOnlineR getRootAsShakeOnlineR(ByteBuffer _bb, ShakeOnlineR obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; }
  public ShakeOnlineR __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public int code() { int o = __offset(4); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public String msg() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer msgAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public ByteBuffer msgInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 6, 1); }
  public String id() { int o = __offset(8); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer idAsByteBuffer() { return __vector_as_bytebuffer(8, 1); }
  public ByteBuffer idInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 8, 1); }
  public String token() { int o = __offset(10); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer tokenAsByteBuffer() { return __vector_as_bytebuffer(10, 1); }
  public ByteBuffer tokenInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 10, 1); }
  public String server() { int o = __offset(12); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer serverAsByteBuffer() { return __vector_as_bytebuffer(12, 1); }
  public ByteBuffer serverInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 12, 1); }
  public String app() { int o = __offset(14); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer appAsByteBuffer() { return __vector_as_bytebuffer(14, 1); }
  public ByteBuffer appInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 14, 1); }
  public int type() { int o = __offset(16); return o != 0 ? bb.getInt(o + bb_pos) : 0; }

  public static int createShakeOnlineR(FlatBufferBuilder builder,
      int code,
      int msgOffset,
      int idOffset,
      int tokenOffset,
      int serverOffset,
      int appOffset,
      int type) {
    builder.startObject(7);
    ShakeOnlineR.addType(builder, type);
    ShakeOnlineR.addApp(builder, appOffset);
    ShakeOnlineR.addServer(builder, serverOffset);
    ShakeOnlineR.addToken(builder, tokenOffset);
    ShakeOnlineR.addId(builder, idOffset);
    ShakeOnlineR.addMsg(builder, msgOffset);
    ShakeOnlineR.addCode(builder, code);
    return ShakeOnlineR.endShakeOnlineR(builder);
  }

  public static void startShakeOnlineR(FlatBufferBuilder builder) { builder.startObject(7); }
  public static void addCode(FlatBufferBuilder builder, int code) { builder.addInt(0, code, 0); }
  public static void addMsg(FlatBufferBuilder builder, int msgOffset) { builder.addOffset(1, msgOffset, 0); }
  public static void addId(FlatBufferBuilder builder, int idOffset) { builder.addOffset(2, idOffset, 0); }
  public static void addToken(FlatBufferBuilder builder, int tokenOffset) { builder.addOffset(3, tokenOffset, 0); }
  public static void addServer(FlatBufferBuilder builder, int serverOffset) { builder.addOffset(4, serverOffset, 0); }
  public static void addApp(FlatBufferBuilder builder, int appOffset) { builder.addOffset(5, appOffset, 0); }
  public static void addType(FlatBufferBuilder builder, int type) { builder.addInt(6, type, 0); }
  public static int endShakeOnlineR(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
}

