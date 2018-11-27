// automatically generated by the FlatBuffers compiler, do not modify

package CSProto;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class InputTouch extends Table {
  public static InputTouch getRootAsInputTouch(ByteBuffer _bb) { return getRootAsInputTouch(_bb, new InputTouch()); }
  public static InputTouch getRootAsInputTouch(ByteBuffer _bb, InputTouch obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; }
  public InputTouch __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public int index() { int o = __offset(4); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public int type() { int o = __offset(6); return o != 0 ? bb.get(o + bb_pos) & 0xFF : 0; }
  public TouchInfo touch(int j) { return touch(new TouchInfo(), j); }
  public TouchInfo touch(TouchInfo obj, int j) { int o = __offset(8); return o != 0 ? obj.__assign(__vector(o) + j * 12, bb) : null; }
  public int touchLength() { int o = __offset(8); return o != 0 ? __vector_len(o) : 0; }

  public static int createInputTouch(FlatBufferBuilder builder,
      int index,
      int type,
      int touchOffset) {
    builder.startObject(3);
    InputTouch.addTouch(builder, touchOffset);
    InputTouch.addIndex(builder, index);
    InputTouch.addType(builder, type);
    return InputTouch.endInputTouch(builder);
  }

  public static void startInputTouch(FlatBufferBuilder builder) { builder.startObject(3); }
  public static void addIndex(FlatBufferBuilder builder, int index) { builder.addInt(0, index, 0); }
  public static void addType(FlatBufferBuilder builder, int type) { builder.addByte(1, (byte)type, (byte)0); }
  public static void addTouch(FlatBufferBuilder builder, int touchOffset) { builder.addOffset(2, touchOffset, 0); }
  public static void startTouchVector(FlatBufferBuilder builder, int numElems) { builder.startVector(12, numElems, 4); }
  public static int endInputTouch(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
}
