// automatically generated by the FlatBuffers compiler, do not modify

package CSProto;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class InputAmbientLight extends Table {
  public static InputAmbientLight getRootAsInputAmbientLight(ByteBuffer _bb) { return getRootAsInputAmbientLight(_bb, new InputAmbientLight()); }
  public static InputAmbientLight getRootAsInputAmbientLight(ByteBuffer _bb, InputAmbientLight obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; }
  public InputAmbientLight __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public float reserved() { int o = __offset(4); return o != 0 ? bb.getFloat(o + bb_pos) : 0.0f; }

  public static int createInputAmbientLight(FlatBufferBuilder builder,
      float reserved) {
    builder.startObject(1);
    InputAmbientLight.addReserved(builder, reserved);
    return InputAmbientLight.endInputAmbientLight(builder);
  }

  public static void startInputAmbientLight(FlatBufferBuilder builder) { builder.startObject(1); }
  public static void addReserved(FlatBufferBuilder builder, float reserved) { builder.addFloat(0, reserved, 0.0f); }
  public static int endInputAmbientLight(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
}
