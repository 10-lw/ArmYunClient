// automatically generated by the FlatBuffers compiler, do not modify

package CSProto;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class ControlVideo extends Table {
  public static ControlVideo getRootAsControlVideo(ByteBuffer _bb) { return getRootAsControlVideo(_bb, new ControlVideo()); }
  public static ControlVideo getRootAsControlVideo(ByteBuffer _bb, ControlVideo obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; }
  public ControlVideo __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public int mode() { int o = __offset(4); return o != 0 ? bb.get(o + bb_pos) & 0xFF : 0; }
  public long bitrate() { int o = __offset(6); return o != 0 ? (long)bb.getInt(o + bb_pos) & 0xFFFFFFFFL : 5242880L; }
  public long fps() { int o = __offset(8); return o != 0 ? (long)bb.getInt(o + bb_pos) & 0xFFFFFFFFL : 30L; }
  public long maxiframe() { int o = __offset(10); return o != 0 ? (long)bb.getInt(o + bb_pos) & 0xFFFFFFFFL : 100L; }

  public static int createControlVideo(FlatBufferBuilder builder,
      int mode,
      long bitrate,
      long fps,
      long maxiframe) {
    builder.startObject(4);
    ControlVideo.addMaxiframe(builder, maxiframe);
    ControlVideo.addFps(builder, fps);
    ControlVideo.addBitrate(builder, bitrate);
    ControlVideo.addMode(builder, mode);
    return ControlVideo.endControlVideo(builder);
  }

  public static void startControlVideo(FlatBufferBuilder builder) { builder.startObject(4); }
  public static void addMode(FlatBufferBuilder builder, int mode) { builder.addByte(0, (byte)mode, (byte)0); }
  public static void addBitrate(FlatBufferBuilder builder, long bitrate) { builder.addInt(1, (int)bitrate, (int)5242880L); }
  public static void addFps(FlatBufferBuilder builder, long fps) { builder.addInt(2, (int)fps, (int)30L); }
  public static void addMaxiframe(FlatBufferBuilder builder, long maxiframe) { builder.addInt(3, (int)maxiframe, (int)100L); }
  public static int endControlVideo(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
}
