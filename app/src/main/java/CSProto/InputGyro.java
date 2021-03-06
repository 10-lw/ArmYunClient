// automatically generated by the FlatBuffers compiler, do not modify

package CSProto;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class InputGyro extends Table {
  public static InputGyro getRootAsInputGyro(ByteBuffer _bb) { return getRootAsInputGyro(_bb, new InputGyro()); }
  public static InputGyro getRootAsInputGyro(ByteBuffer _bb, InputGyro obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; }
  public InputGyro __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public float x() { int o = __offset(4); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public float y() { int o = __offset(6); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }
  public float z() { int o = __offset(8); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }

  public static int createInputGyro(FlatBufferBuilder builder,
      float x,
      float y,
      float z) {
    builder.startObject(3);
    InputGyro.addZ(builder, z);
    InputGyro.addY(builder, y);
    InputGyro.addX(builder, x);
    return InputGyro.endInputGyro(builder);
  }

  public static void startInputGyro(FlatBufferBuilder builder) { builder.startObject(3); }
  public static void addX(FlatBufferBuilder builder, float x) { builder.addFloat(0, x, 0.0f); }
  public static void addY(FlatBufferBuilder builder, float y) { builder.addFloat(1, y, 0.0f); }
  public static void addZ(FlatBufferBuilder builder, float z) { builder.addFloat(2, z, 0.0f); }
  public static int endInputGyro(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
}

