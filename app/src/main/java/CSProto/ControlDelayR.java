// automatically generated by the FlatBuffers compiler, do not modify

package CSProto;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class ControlDelayR extends Table {
  public static ControlDelayR getRootAsControlDelayR(ByteBuffer _bb) { return getRootAsControlDelayR(_bb, new ControlDelayR()); }
  public static ControlDelayR getRootAsControlDelayR(ByteBuffer _bb, ControlDelayR obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; }
  public ControlDelayR __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public String time() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer timeAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer timeInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }
  public byte data(int j) { int o = __offset(6); return o != 0 ? bb.get(__vector(o) + j * 1) : 0; }
  public int dataLength() { int o = __offset(6); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer dataAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public ByteBuffer dataInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 6, 1); }

  public static int createControlDelayR(FlatBufferBuilder builder,
      int timeOffset,
      int dataOffset) {
    builder.startObject(2);
    ControlDelayR.addData(builder, dataOffset);
    ControlDelayR.addTime(builder, timeOffset);
    return ControlDelayR.endControlDelayR(builder);
  }

  public static void startControlDelayR(FlatBufferBuilder builder) { builder.startObject(2); }
  public static void addTime(FlatBufferBuilder builder, int timeOffset) { builder.addOffset(0, timeOffset, 0); }
  public static void addData(FlatBufferBuilder builder, int dataOffset) { builder.addOffset(1, dataOffset, 0); }
  public static int createDataVector(FlatBufferBuilder builder, byte[] data) { builder.startVector(1, data.length, 1); for (int i = data.length - 1; i >= 0; i--) builder.addByte(data[i]); return builder.endVector(); }
  public static void startDataVector(FlatBufferBuilder builder, int numElems) { builder.startVector(1, numElems, 1); }
  public static int endControlDelayR(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
}
