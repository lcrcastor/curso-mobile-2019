package android.support.media;

import android.content.res.AssetManager.AssetInputStream;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.view.InputDeviceCompat;
import android.util.Log;
import android.util.Pair;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.utils.localizacion.LocalizacionManager;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.common.base.Ascii;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.message.TokenParser;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bouncycastle.apache.bzip2.BZip2Constants;

public class ExifInterface {
    private static final HashSet<String> A = new HashSet<>(Arrays.asList(new String[]{TAG_F_NUMBER, TAG_DIGITAL_ZOOM_RATIO, TAG_EXPOSURE_TIME, TAG_SUBJECT_DISTANCE, TAG_GPS_TIMESTAMP}));
    public static final short ALTITUDE_ABOVE_SEA_LEVEL = 0;
    public static final short ALTITUDE_BELOW_SEA_LEVEL = 1;
    private static final HashMap<Integer, Integer> B = new HashMap<>();
    public static final int[] BITS_PER_SAMPLE_GREYSCALE_1 = {4};
    public static final int[] BITS_PER_SAMPLE_GREYSCALE_2 = {8};
    public static final int[] BITS_PER_SAMPLE_RGB = {8, 8, 8};
    /* access modifiers changed from: private */
    public static final Charset C = Charset.forName("US-ASCII");
    public static final int COLOR_SPACE_S_RGB = 1;
    public static final int COLOR_SPACE_UNCALIBRATED = 65535;
    public static final short CONTRAST_HARD = 2;
    public static final short CONTRAST_NORMAL = 0;
    public static final short CONTRAST_SOFT = 1;
    public static final int DATA_DEFLATE_ZIP = 8;
    public static final int DATA_HUFFMAN_COMPRESSED = 2;
    public static final int DATA_JPEG = 6;
    public static final int DATA_JPEG_COMPRESSED = 7;
    public static final int DATA_LOSSY_JPEG = 34892;
    public static final int DATA_PACK_BITS_COMPRESSED = 32773;
    public static final int DATA_UNCOMPRESSED = 1;
    public static final short EXPOSURE_MODE_AUTO = 0;
    public static final short EXPOSURE_MODE_AUTO_BRACKET = 2;
    public static final short EXPOSURE_MODE_MANUAL = 1;
    public static final short EXPOSURE_PROGRAM_ACTION = 6;
    public static final short EXPOSURE_PROGRAM_APERTURE_PRIORITY = 3;
    public static final short EXPOSURE_PROGRAM_CREATIVE = 5;
    public static final short EXPOSURE_PROGRAM_LANDSCAPE_MODE = 8;
    public static final short EXPOSURE_PROGRAM_MANUAL = 1;
    public static final short EXPOSURE_PROGRAM_NORMAL = 2;
    public static final short EXPOSURE_PROGRAM_NOT_DEFINED = 0;
    public static final short EXPOSURE_PROGRAM_PORTRAIT_MODE = 7;
    public static final short EXPOSURE_PROGRAM_SHUTTER_PRIORITY = 4;
    public static final short FILE_SOURCE_DSC = 3;
    public static final short FILE_SOURCE_OTHER = 0;
    public static final short FILE_SOURCE_REFLEX_SCANNER = 2;
    public static final short FILE_SOURCE_TRANSPARENT_SCANNER = 1;
    public static final short FLAG_FLASH_FIRED = 1;
    public static final short FLAG_FLASH_MODE_AUTO = 24;
    public static final short FLAG_FLASH_MODE_COMPULSORY_FIRING = 8;
    public static final short FLAG_FLASH_MODE_COMPULSORY_SUPPRESSION = 16;
    public static final short FLAG_FLASH_NO_FLASH_FUNCTION = 32;
    public static final short FLAG_FLASH_RED_EYE_SUPPORTED = 64;
    public static final short FLAG_FLASH_RETURN_LIGHT_DETECTED = 6;
    public static final short FLAG_FLASH_RETURN_LIGHT_NOT_DETECTED = 4;
    public static final short FORMAT_CHUNKY = 1;
    public static final short FORMAT_PLANAR = 2;
    public static final short GAIN_CONTROL_HIGH_GAIN_DOWN = 4;
    public static final short GAIN_CONTROL_HIGH_GAIN_UP = 2;
    public static final short GAIN_CONTROL_LOW_GAIN_DOWN = 3;
    public static final short GAIN_CONTROL_LOW_GAIN_UP = 1;
    public static final short GAIN_CONTROL_NONE = 0;
    public static final String GPS_DIRECTION_MAGNETIC = "M";
    public static final String GPS_DIRECTION_TRUE = "T";
    public static final String GPS_DISTANCE_KILOMETERS = "K";
    public static final String GPS_DISTANCE_MILES = "M";
    public static final String GPS_DISTANCE_NAUTICAL_MILES = "N";
    public static final String GPS_MEASUREMENT_2D = "2";
    public static final String GPS_MEASUREMENT_3D = "3";
    public static final short GPS_MEASUREMENT_DIFFERENTIAL_CORRECTED = 1;
    public static final String GPS_MEASUREMENT_INTERRUPTED = "V";
    public static final String GPS_MEASUREMENT_IN_PROGRESS = "A";
    public static final short GPS_MEASUREMENT_NO_DIFFERENTIAL = 0;
    public static final String GPS_SPEED_KILOMETERS_PER_HOUR = "K";
    public static final String GPS_SPEED_KNOTS = "N";
    public static final String GPS_SPEED_MILES_PER_HOUR = "M";
    public static final String LATITUDE_NORTH = "N";
    public static final String LATITUDE_SOUTH = "S";
    public static final short LIGHT_SOURCE_CLOUDY_WEATHER = 10;
    public static final short LIGHT_SOURCE_COOL_WHITE_FLUORESCENT = 14;
    public static final short LIGHT_SOURCE_D50 = 23;
    public static final short LIGHT_SOURCE_D55 = 20;
    public static final short LIGHT_SOURCE_D65 = 21;
    public static final short LIGHT_SOURCE_D75 = 22;
    public static final short LIGHT_SOURCE_DAYLIGHT = 1;
    public static final short LIGHT_SOURCE_DAYLIGHT_FLUORESCENT = 12;
    public static final short LIGHT_SOURCE_DAY_WHITE_FLUORESCENT = 13;
    public static final short LIGHT_SOURCE_FINE_WEATHER = 9;
    public static final short LIGHT_SOURCE_FLASH = 4;
    public static final short LIGHT_SOURCE_FLUORESCENT = 2;
    public static final short LIGHT_SOURCE_ISO_STUDIO_TUNGSTEN = 24;
    public static final short LIGHT_SOURCE_OTHER = 255;
    public static final short LIGHT_SOURCE_SHADE = 11;
    public static final short LIGHT_SOURCE_STANDARD_LIGHT_A = 17;
    public static final short LIGHT_SOURCE_STANDARD_LIGHT_B = 18;
    public static final short LIGHT_SOURCE_STANDARD_LIGHT_C = 19;
    public static final short LIGHT_SOURCE_TUNGSTEN = 3;
    public static final short LIGHT_SOURCE_UNKNOWN = 0;
    public static final short LIGHT_SOURCE_WARM_WHITE_FLUORESCENT = 16;
    public static final short LIGHT_SOURCE_WHITE_FLUORESCENT = 15;
    public static final String LONGITUDE_EAST = "E";
    public static final String LONGITUDE_WEST = "W";
    public static final short METERING_MODE_AVERAGE = 1;
    public static final short METERING_MODE_CENTER_WEIGHT_AVERAGE = 2;
    public static final short METERING_MODE_MULTI_SPOT = 4;
    public static final short METERING_MODE_OTHER = 255;
    public static final short METERING_MODE_PARTIAL = 6;
    public static final short METERING_MODE_PATTERN = 5;
    public static final short METERING_MODE_SPOT = 3;
    public static final short METERING_MODE_UNKNOWN = 0;
    public static final int ORIENTATION_FLIP_HORIZONTAL = 2;
    public static final int ORIENTATION_FLIP_VERTICAL = 4;
    public static final int ORIENTATION_NORMAL = 1;
    public static final int ORIENTATION_ROTATE_180 = 3;
    public static final int ORIENTATION_ROTATE_270 = 8;
    public static final int ORIENTATION_ROTATE_90 = 6;
    public static final int ORIENTATION_TRANSPOSE = 5;
    public static final int ORIENTATION_TRANSVERSE = 7;
    public static final int ORIENTATION_UNDEFINED = 0;
    public static final int ORIGINAL_RESOLUTION_IMAGE = 0;
    public static final int PHOTOMETRIC_INTERPRETATION_BLACK_IS_ZERO = 1;
    public static final int PHOTOMETRIC_INTERPRETATION_RGB = 2;
    public static final int PHOTOMETRIC_INTERPRETATION_WHITE_IS_ZERO = 0;
    public static final int PHOTOMETRIC_INTERPRETATION_YCBCR = 6;
    public static final int REDUCED_RESOLUTION_IMAGE = 1;
    public static final short RENDERED_PROCESS_CUSTOM = 1;
    public static final short RENDERED_PROCESS_NORMAL = 0;
    public static final short RESOLUTION_UNIT_CENTIMETERS = 3;
    public static final short RESOLUTION_UNIT_INCHES = 2;
    public static final short SATURATION_HIGH = 0;
    public static final short SATURATION_LOW = 0;
    public static final short SATURATION_NORMAL = 0;
    public static final short SCENE_CAPTURE_TYPE_LANDSCAPE = 1;
    public static final short SCENE_CAPTURE_TYPE_NIGHT = 3;
    public static final short SCENE_CAPTURE_TYPE_PORTRAIT = 2;
    public static final short SCENE_CAPTURE_TYPE_STANDARD = 0;
    public static final short SCENE_TYPE_DIRECTLY_PHOTOGRAPHED = 1;
    public static final short SENSITIVITY_TYPE_ISO_SPEED = 3;
    public static final short SENSITIVITY_TYPE_REI = 2;
    public static final short SENSITIVITY_TYPE_REI_AND_ISO = 6;
    public static final short SENSITIVITY_TYPE_SOS = 1;
    public static final short SENSITIVITY_TYPE_SOS_AND_ISO = 5;
    public static final short SENSITIVITY_TYPE_SOS_AND_REI = 4;
    public static final short SENSITIVITY_TYPE_SOS_AND_REI_AND_ISO = 7;
    public static final short SENSITIVITY_TYPE_UNKNOWN = 0;
    public static final short SENSOR_TYPE_COLOR_SEQUENTIAL = 5;
    public static final short SENSOR_TYPE_COLOR_SEQUENTIAL_LINEAR = 8;
    public static final short SENSOR_TYPE_NOT_DEFINED = 1;
    public static final short SENSOR_TYPE_ONE_CHIP = 2;
    public static final short SENSOR_TYPE_THREE_CHIP = 4;
    public static final short SENSOR_TYPE_TRILINEAR = 7;
    public static final short SENSOR_TYPE_TWO_CHIP = 3;
    public static final short SHARPNESS_HARD = 2;
    public static final short SHARPNESS_NORMAL = 0;
    public static final short SHARPNESS_SOFT = 1;
    public static final short SUBJECT_DISTANCE_RANGE_CLOSE_VIEW = 2;
    public static final short SUBJECT_DISTANCE_RANGE_DISTANT_VIEW = 3;
    public static final short SUBJECT_DISTANCE_RANGE_MACRO = 1;
    public static final short SUBJECT_DISTANCE_RANGE_UNKNOWN = 0;
    private static final Pattern T = Pattern.compile(".*[1-9].*");
    public static final String TAG_APERTURE_VALUE = "ApertureValue";
    public static final String TAG_ARTIST = "Artist";
    public static final String TAG_BITS_PER_SAMPLE = "BitsPerSample";
    public static final String TAG_BODY_SERIAL_NUMBER = "BodySerialNumber";
    public static final String TAG_BRIGHTNESS_VALUE = "BrightnessValue";
    public static final String TAG_CAMARA_OWNER_NAME = "CameraOwnerName";
    public static final String TAG_CFA_PATTERN = "CFAPattern";
    public static final String TAG_COLOR_SPACE = "ColorSpace";
    public static final String TAG_COMPONENTS_CONFIGURATION = "ComponentsConfiguration";
    public static final String TAG_COMPRESSED_BITS_PER_PIXEL = "CompressedBitsPerPixel";
    public static final String TAG_COMPRESSION = "Compression";
    public static final String TAG_CONTRAST = "Contrast";
    public static final String TAG_COPYRIGHT = "Copyright";
    public static final String TAG_CUSTOM_RENDERED = "CustomRendered";
    public static final String TAG_DATETIME = "DateTime";
    public static final String TAG_DATETIME_DIGITIZED = "DateTimeDigitized";
    public static final String TAG_DATETIME_ORIGINAL = "DateTimeOriginal";
    public static final String TAG_DEFAULT_CROP_SIZE = "DefaultCropSize";
    public static final String TAG_DEVICE_SETTING_DESCRIPTION = "DeviceSettingDescription";
    public static final String TAG_DIGITAL_ZOOM_RATIO = "DigitalZoomRatio";
    public static final String TAG_DNG_VERSION = "DNGVersion";
    public static final String TAG_EXIF_VERSION = "ExifVersion";
    public static final String TAG_EXPOSURE_BIAS_VALUE = "ExposureBiasValue";
    public static final String TAG_EXPOSURE_INDEX = "ExposureIndex";
    public static final String TAG_EXPOSURE_MODE = "ExposureMode";
    public static final String TAG_EXPOSURE_PROGRAM = "ExposureProgram";
    public static final String TAG_EXPOSURE_TIME = "ExposureTime";
    public static final String TAG_FILE_SOURCE = "FileSource";
    public static final String TAG_FLASH = "Flash";
    public static final String TAG_FLASHPIX_VERSION = "FlashpixVersion";
    public static final String TAG_FLASH_ENERGY = "FlashEnergy";
    public static final String TAG_FOCAL_LENGTH = "FocalLength";
    public static final String TAG_FOCAL_LENGTH_IN_35MM_FILM = "FocalLengthIn35mmFilm";
    public static final String TAG_FOCAL_PLANE_RESOLUTION_UNIT = "FocalPlaneResolutionUnit";
    public static final String TAG_FOCAL_PLANE_X_RESOLUTION = "FocalPlaneXResolution";
    public static final String TAG_FOCAL_PLANE_Y_RESOLUTION = "FocalPlaneYResolution";
    public static final String TAG_F_NUMBER = "FNumber";
    public static final String TAG_GAIN_CONTROL = "GainControl";
    public static final String TAG_GAMMA = "Gamma";
    public static final String TAG_GPS_ALTITUDE = "GPSAltitude";
    public static final String TAG_GPS_ALTITUDE_REF = "GPSAltitudeRef";
    public static final String TAG_GPS_AREA_INFORMATION = "GPSAreaInformation";
    public static final String TAG_GPS_DATESTAMP = "GPSDateStamp";
    public static final String TAG_GPS_DEST_BEARING = "GPSDestBearing";
    public static final String TAG_GPS_DEST_BEARING_REF = "GPSDestBearingRef";
    public static final String TAG_GPS_DEST_DISTANCE = "GPSDestDistance";
    public static final String TAG_GPS_DEST_DISTANCE_REF = "GPSDestDistanceRef";
    public static final String TAG_GPS_DEST_LATITUDE = "GPSDestLatitude";
    public static final String TAG_GPS_DEST_LATITUDE_REF = "GPSDestLatitudeRef";
    public static final String TAG_GPS_DEST_LONGITUDE = "GPSDestLongitude";
    public static final String TAG_GPS_DEST_LONGITUDE_REF = "GPSDestLongitudeRef";
    public static final String TAG_GPS_DIFFERENTIAL = "GPSDifferential";
    public static final String TAG_GPS_DOP = "GPSDOP";
    public static final String TAG_GPS_H_POSITIONING_ERROR = "GPSHPositioningError";
    public static final String TAG_GPS_IMG_DIRECTION = "GPSImgDirection";
    public static final String TAG_GPS_IMG_DIRECTION_REF = "GPSImgDirectionRef";
    public static final String TAG_GPS_LATITUDE = "GPSLatitude";
    public static final String TAG_GPS_LATITUDE_REF = "GPSLatitudeRef";
    public static final String TAG_GPS_LONGITUDE = "GPSLongitude";
    public static final String TAG_GPS_LONGITUDE_REF = "GPSLongitudeRef";
    public static final String TAG_GPS_MAP_DATUM = "GPSMapDatum";
    public static final String TAG_GPS_MEASURE_MODE = "GPSMeasureMode";
    public static final String TAG_GPS_PROCESSING_METHOD = "GPSProcessingMethod";
    public static final String TAG_GPS_SATELLITES = "GPSSatellites";
    public static final String TAG_GPS_SPEED = "GPSSpeed";
    public static final String TAG_GPS_SPEED_REF = "GPSSpeedRef";
    public static final String TAG_GPS_STATUS = "GPSStatus";
    public static final String TAG_GPS_TIMESTAMP = "GPSTimeStamp";
    public static final String TAG_GPS_TRACK = "GPSTrack";
    public static final String TAG_GPS_TRACK_REF = "GPSTrackRef";
    public static final String TAG_GPS_VERSION_ID = "GPSVersionID";
    public static final String TAG_IMAGE_DESCRIPTION = "ImageDescription";
    public static final String TAG_IMAGE_LENGTH = "ImageLength";
    public static final String TAG_IMAGE_UNIQUE_ID = "ImageUniqueID";
    public static final String TAG_IMAGE_WIDTH = "ImageWidth";
    public static final String TAG_INTEROPERABILITY_INDEX = "InteroperabilityIndex";
    public static final String TAG_ISO_SPEED = "ISOSpeed";
    public static final String TAG_ISO_SPEED_LATITUDE_YYY = "ISOSpeedLatitudeyyy";
    public static final String TAG_ISO_SPEED_LATITUDE_ZZZ = "ISOSpeedLatitudezzz";
    @Deprecated
    public static final String TAG_ISO_SPEED_RATINGS = "ISOSpeedRatings";
    public static final String TAG_JPEG_INTERCHANGE_FORMAT = "JPEGInterchangeFormat";
    public static final String TAG_JPEG_INTERCHANGE_FORMAT_LENGTH = "JPEGInterchangeFormatLength";
    public static final String TAG_LENS_MAKE = "LensMake";
    public static final String TAG_LENS_MODEL = "LensModel";
    public static final String TAG_LENS_SERIAL_NUMBER = "LensSerialNumber";
    public static final String TAG_LENS_SPECIFICATION = "LensSpecification";
    public static final String TAG_LIGHT_SOURCE = "LightSource";
    public static final String TAG_MAKE = "Make";
    public static final String TAG_MAKER_NOTE = "MakerNote";
    public static final String TAG_MAX_APERTURE_VALUE = "MaxApertureValue";
    public static final String TAG_METERING_MODE = "MeteringMode";
    public static final String TAG_MODEL = "Model";
    public static final String TAG_NEW_SUBFILE_TYPE = "NewSubfileType";
    public static final String TAG_OECF = "OECF";
    public static final String TAG_ORF_ASPECT_FRAME = "AspectFrame";
    public static final String TAG_ORF_PREVIEW_IMAGE_LENGTH = "PreviewImageLength";
    public static final String TAG_ORF_PREVIEW_IMAGE_START = "PreviewImageStart";
    public static final String TAG_ORF_THUMBNAIL_IMAGE = "ThumbnailImage";
    public static final String TAG_ORIENTATION = "Orientation";
    public static final String TAG_PHOTOGRAPHIC_SENSITIVITY = "PhotographicSensitivity";
    public static final String TAG_PHOTOMETRIC_INTERPRETATION = "PhotometricInterpretation";
    public static final String TAG_PIXEL_X_DIMENSION = "PixelXDimension";
    public static final String TAG_PIXEL_Y_DIMENSION = "PixelYDimension";
    public static final String TAG_PLANAR_CONFIGURATION = "PlanarConfiguration";
    public static final String TAG_PRIMARY_CHROMATICITIES = "PrimaryChromaticities";
    public static final String TAG_RECOMMENDED_EXPOSURE_INDEX = "RecommendedExposureIndex";
    public static final String TAG_REFERENCE_BLACK_WHITE = "ReferenceBlackWhite";
    public static final String TAG_RELATED_SOUND_FILE = "RelatedSoundFile";
    public static final String TAG_RESOLUTION_UNIT = "ResolutionUnit";
    public static final String TAG_ROWS_PER_STRIP = "RowsPerStrip";
    public static final String TAG_RW2_ISO = "ISO";
    public static final String TAG_RW2_JPG_FROM_RAW = "JpgFromRaw";
    public static final String TAG_RW2_SENSOR_BOTTOM_BORDER = "SensorBottomBorder";
    public static final String TAG_RW2_SENSOR_LEFT_BORDER = "SensorLeftBorder";
    public static final String TAG_RW2_SENSOR_RIGHT_BORDER = "SensorRightBorder";
    public static final String TAG_RW2_SENSOR_TOP_BORDER = "SensorTopBorder";
    public static final String TAG_SAMPLES_PER_PIXEL = "SamplesPerPixel";
    public static final String TAG_SATURATION = "Saturation";
    public static final String TAG_SCENE_CAPTURE_TYPE = "SceneCaptureType";
    public static final String TAG_SCENE_TYPE = "SceneType";
    public static final String TAG_SENSING_METHOD = "SensingMethod";
    public static final String TAG_SENSITIVITY_TYPE = "SensitivityType";
    public static final String TAG_SHARPNESS = "Sharpness";
    public static final String TAG_SHUTTER_SPEED_VALUE = "ShutterSpeedValue";
    public static final String TAG_SOFTWARE = "Software";
    public static final String TAG_SPATIAL_FREQUENCY_RESPONSE = "SpatialFrequencyResponse";
    public static final String TAG_SPECTRAL_SENSITIVITY = "SpectralSensitivity";
    public static final String TAG_STANDARD_OUTPUT_SENSITIVITY = "StandardOutputSensitivity";
    public static final String TAG_STRIP_BYTE_COUNTS = "StripByteCounts";
    public static final String TAG_STRIP_OFFSETS = "StripOffsets";
    public static final String TAG_SUBFILE_TYPE = "SubfileType";
    public static final String TAG_SUBJECT_AREA = "SubjectArea";
    public static final String TAG_SUBJECT_DISTANCE = "SubjectDistance";
    public static final String TAG_SUBJECT_DISTANCE_RANGE = "SubjectDistanceRange";
    public static final String TAG_SUBJECT_LOCATION = "SubjectLocation";
    public static final String TAG_SUBSEC_TIME = "SubSecTime";
    public static final String TAG_SUBSEC_TIME_DIGITIZED = "SubSecTimeDigitized";
    public static final String TAG_SUBSEC_TIME_ORIGINAL = "SubSecTimeOriginal";
    public static final String TAG_THUMBNAIL_IMAGE_LENGTH = "ThumbnailImageLength";
    public static final String TAG_THUMBNAIL_IMAGE_WIDTH = "ThumbnailImageWidth";
    public static final String TAG_TRANSFER_FUNCTION = "TransferFunction";
    public static final String TAG_USER_COMMENT = "UserComment";
    public static final String TAG_WHITE_BALANCE = "WhiteBalance";
    public static final String TAG_WHITE_POINT = "WhitePoint";
    public static final String TAG_X_RESOLUTION = "XResolution";
    public static final String TAG_Y_CB_CR_COEFFICIENTS = "YCbCrCoefficients";
    public static final String TAG_Y_CB_CR_POSITIONING = "YCbCrPositioning";
    public static final String TAG_Y_CB_CR_SUB_SAMPLING = "YCbCrSubSampling";
    public static final String TAG_Y_RESOLUTION = "YResolution";
    private static final Pattern U = Pattern.compile("^([0-9][0-9]):([0-9][0-9]):([0-9][0-9])$");
    @Deprecated
    public static final int WHITEBALANCE_AUTO = 0;
    @Deprecated
    public static final int WHITEBALANCE_MANUAL = 1;
    public static final short WHITE_BALANCE_AUTO = 0;
    public static final short WHITE_BALANCE_MANUAL = 1;
    public static final short Y_CB_CR_POSITIONING_CENTERED = 1;
    public static final short Y_CB_CR_POSITIONING_CO_SITED = 2;
    static final byte[] a = {-1, -40, -1};
    static final String[] b = {"", "BYTE", "STRING", "USHORT", "ULONG", "URATIONAL", "SBYTE", "UNDEFINED", "SSHORT", "SLONG", "SRATIONAL", "SINGLE", "DOUBLE"};
    static final int[] c = {0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8, 1};
    static final ExifTag[][] d = {l, m, n, o, p, l, r, s, t, u};
    static final byte[] e = "Exif\u0000\u0000".getBytes(C);
    private static final List<Integer> f = Arrays.asList(new Integer[]{Integer.valueOf(1), Integer.valueOf(6), Integer.valueOf(3), Integer.valueOf(8)});
    private static final List<Integer> g = Arrays.asList(new Integer[]{Integer.valueOf(2), Integer.valueOf(7), Integer.valueOf(4), Integer.valueOf(5)});
    private static final byte[] h = {79, 76, 89, 77, 80, 0};
    private static final byte[] i = {79, 76, 89, 77, 80, 85, 83, 0, 73, 73};
    private static SimpleDateFormat j = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
    /* access modifiers changed from: private */
    public static final byte[] k = {65, 83, 67, 73, 73, 0, 0, 0};
    private static final ExifTag[] l;
    private static final ExifTag[] m;
    private static final ExifTag[] n = {new ExifTag(TAG_GPS_VERSION_ID, 0, 1), new ExifTag(TAG_GPS_LATITUDE_REF, 1, 2), new ExifTag(TAG_GPS_LATITUDE, 2, 5), new ExifTag(TAG_GPS_LONGITUDE_REF, 3, 2), new ExifTag(TAG_GPS_LONGITUDE, 4, 5), new ExifTag(TAG_GPS_ALTITUDE_REF, 5, 1), new ExifTag(TAG_GPS_ALTITUDE, 6, 5), new ExifTag(TAG_GPS_TIMESTAMP, 7, 5), new ExifTag(TAG_GPS_SATELLITES, 8, 2), new ExifTag(TAG_GPS_STATUS, 9, 2), new ExifTag(TAG_GPS_MEASURE_MODE, 10, 2), new ExifTag(TAG_GPS_DOP, 11, 5), new ExifTag(TAG_GPS_SPEED_REF, 12, 2), new ExifTag(TAG_GPS_SPEED, 13, 5), new ExifTag(TAG_GPS_TRACK_REF, 14, 2), new ExifTag(TAG_GPS_TRACK, 15, 5), new ExifTag(TAG_GPS_IMG_DIRECTION_REF, 16, 2), new ExifTag(TAG_GPS_IMG_DIRECTION, 17, 5), new ExifTag(TAG_GPS_MAP_DATUM, 18, 2), new ExifTag(TAG_GPS_DEST_LATITUDE_REF, 19, 2), new ExifTag(TAG_GPS_DEST_LATITUDE, 20, 5), new ExifTag(TAG_GPS_DEST_LONGITUDE_REF, 21, 2), new ExifTag(TAG_GPS_DEST_LONGITUDE, 22, 5), new ExifTag(TAG_GPS_DEST_BEARING_REF, 23, 2), new ExifTag(TAG_GPS_DEST_BEARING, 24, 5), new ExifTag(TAG_GPS_DEST_DISTANCE_REF, 25, 2), new ExifTag(TAG_GPS_DEST_DISTANCE, 26, 5), new ExifTag(TAG_GPS_PROCESSING_METHOD, 27, 7), new ExifTag(TAG_GPS_AREA_INFORMATION, 28, 7), new ExifTag(TAG_GPS_DATESTAMP, 29, 2), new ExifTag(TAG_GPS_DIFFERENTIAL, 30, 3)};
    private static final ExifTag[] o = {new ExifTag(TAG_INTEROPERABILITY_INDEX, 1, 2)};
    private static final ExifTag[] p;
    private static final ExifTag q = new ExifTag(TAG_STRIP_OFFSETS, 273, 3);
    private static final ExifTag[] r = {new ExifTag(TAG_ORF_THUMBNAIL_IMAGE, 256, 7), new ExifTag("CameraSettingsIFDPointer", 8224, 4), new ExifTag("ImageProcessingIFDPointer", 8256, 4)};
    private static final ExifTag[] s = {new ExifTag(TAG_ORF_PREVIEW_IMAGE_START, 257, 4), new ExifTag(TAG_ORF_PREVIEW_IMAGE_LENGTH, (int) BZip2Constants.MAX_ALPHA_SIZE, 4)};
    private static final ExifTag[] t = {new ExifTag(TAG_ORF_ASPECT_FRAME, 4371, 3)};
    private static final ExifTag[] u = {new ExifTag(TAG_COLOR_SPACE, 55, 3)};
    private static final ExifTag[] v = {new ExifTag("SubIFDPointer", 330, 4), new ExifTag("ExifIFDPointer", 34665, 4), new ExifTag("GPSInfoIFDPointer", 34853, 4), new ExifTag("InteroperabilityIFDPointer", 40965, 4), new ExifTag("CameraSettingsIFDPointer", 8224, 1), new ExifTag("ImageProcessingIFDPointer", 8256, 1)};
    private static final ExifTag w = new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT, (int) InputDeviceCompat.SOURCE_DPAD, 4);
    private static final ExifTag x = new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, 4);
    private static final HashMap<Integer, ExifTag>[] y = new HashMap[d.length];
    private static final HashMap<String, ExifTag>[] z = new HashMap[d.length];
    private final String D;
    private final AssetInputStream E;
    private int F;
    private final HashMap<String, ExifAttribute>[] G = new HashMap[d.length];
    private ByteOrder H = ByteOrder.BIG_ENDIAN;
    private boolean I;
    private int J;
    private int K;
    private byte[] L;
    private int M;
    private int N;
    private int O;
    private int P;
    private int Q;
    private int R;
    private boolean S;

    static class ByteOrderedDataInputStream extends InputStream implements DataInput {
        private static final ByteOrder a = ByteOrder.LITTLE_ENDIAN;
        private static final ByteOrder b = ByteOrder.BIG_ENDIAN;
        private DataInputStream c;
        private ByteOrder d;
        /* access modifiers changed from: private */
        public final int e;
        /* access modifiers changed from: private */
        public int f;

        public ByteOrderedDataInputStream(InputStream inputStream) {
            this.d = ByteOrder.BIG_ENDIAN;
            this.c = new DataInputStream(inputStream);
            this.e = this.c.available();
            this.f = 0;
            this.c.mark(this.e);
        }

        public ByteOrderedDataInputStream(byte[] bArr) {
            this((InputStream) new ByteArrayInputStream(bArr));
        }

        public void a(ByteOrder byteOrder) {
            this.d = byteOrder;
        }

        public void a(long j) {
            if (((long) this.f) > j) {
                this.f = 0;
                this.c.reset();
                this.c.mark(this.e);
            } else {
                j -= (long) this.f;
            }
            int i = (int) j;
            if (skipBytes(i) != i) {
                throw new IOException("Couldn't seek up to the byteCount");
            }
        }

        public int a() {
            return this.f;
        }

        public int available() {
            return this.c.available();
        }

        public int read() {
            this.f++;
            return this.c.read();
        }

        public int read(byte[] bArr, int i, int i2) {
            int read = this.c.read(bArr, i, i2);
            this.f += read;
            return read;
        }

        public int readUnsignedByte() {
            this.f++;
            return this.c.readUnsignedByte();
        }

        public String readLine() {
            Log.d("ExifInterface", "Currently unsupported");
            return null;
        }

        public boolean readBoolean() {
            this.f++;
            return this.c.readBoolean();
        }

        public char readChar() {
            this.f += 2;
            return this.c.readChar();
        }

        public String readUTF() {
            this.f += 2;
            return this.c.readUTF();
        }

        public void readFully(byte[] bArr, int i, int i2) {
            this.f += i2;
            if (this.f > this.e) {
                throw new EOFException();
            } else if (this.c.read(bArr, i, i2) != i2) {
                throw new IOException("Couldn't read up to the length of buffer");
            }
        }

        public void readFully(byte[] bArr) {
            this.f += bArr.length;
            if (this.f > this.e) {
                throw new EOFException();
            } else if (this.c.read(bArr, 0, bArr.length) != bArr.length) {
                throw new IOException("Couldn't read up to the length of buffer");
            }
        }

        public byte readByte() {
            this.f++;
            if (this.f > this.e) {
                throw new EOFException();
            }
            int read = this.c.read();
            if (read >= 0) {
                return (byte) read;
            }
            throw new EOFException();
        }

        public short readShort() {
            this.f += 2;
            if (this.f > this.e) {
                throw new EOFException();
            }
            int read = this.c.read();
            int read2 = this.c.read();
            if ((read | read2) < 0) {
                throw new EOFException();
            } else if (this.d == a) {
                return (short) ((read2 << 8) + read);
            } else {
                if (this.d == b) {
                    return (short) ((read << 8) + read2);
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid byte order: ");
                sb.append(this.d);
                throw new IOException(sb.toString());
            }
        }

        public int readInt() {
            this.f += 4;
            if (this.f > this.e) {
                throw new EOFException();
            }
            int read = this.c.read();
            int read2 = this.c.read();
            int read3 = this.c.read();
            int read4 = this.c.read();
            if ((read | read2 | read3 | read4) < 0) {
                throw new EOFException();
            } else if (this.d == a) {
                return (read4 << 24) + (read3 << 16) + (read2 << 8) + read;
            } else {
                if (this.d == b) {
                    return (read << 24) + (read2 << 16) + (read3 << 8) + read4;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid byte order: ");
                sb.append(this.d);
                throw new IOException(sb.toString());
            }
        }

        public int skipBytes(int i) {
            int min = Math.min(i, this.e - this.f);
            int i2 = 0;
            while (i2 < min) {
                i2 += this.c.skipBytes(min - i2);
            }
            this.f += i2;
            return i2;
        }

        public int readUnsignedShort() {
            this.f += 2;
            if (this.f > this.e) {
                throw new EOFException();
            }
            int read = this.c.read();
            int read2 = this.c.read();
            if ((read | read2) < 0) {
                throw new EOFException();
            } else if (this.d == a) {
                return (read2 << 8) + read;
            } else {
                if (this.d == b) {
                    return (read << 8) + read2;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid byte order: ");
                sb.append(this.d);
                throw new IOException(sb.toString());
            }
        }

        public long b() {
            return ((long) readInt()) & 4294967295L;
        }

        public long readLong() {
            this.f += 8;
            if (this.f > this.e) {
                throw new EOFException();
            }
            int read = this.c.read();
            int read2 = this.c.read();
            int read3 = this.c.read();
            int read4 = this.c.read();
            int read5 = this.c.read();
            int read6 = this.c.read();
            int read7 = this.c.read();
            int read8 = this.c.read();
            if ((read | read2 | read3 | read4 | read5 | read6 | read7 | read8) < 0) {
                throw new EOFException();
            } else if (this.d == a) {
                return (((long) read8) << 56) + (((long) read7) << 48) + (((long) read6) << 40) + (((long) read5) << 32) + (((long) read4) << 24) + (((long) read3) << 16) + (((long) read2) << 8) + ((long) read);
            } else {
                int i = read2;
                if (this.d == b) {
                    return (((long) read) << 56) + (((long) i) << 48) + (((long) read3) << 40) + (((long) read4) << 32) + (((long) read5) << 24) + (((long) read6) << 16) + (((long) read7) << 8) + ((long) read8);
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid byte order: ");
                sb.append(this.d);
                throw new IOException(sb.toString());
            }
        }

        public float readFloat() {
            return Float.intBitsToFloat(readInt());
        }

        public double readDouble() {
            return Double.longBitsToDouble(readLong());
        }
    }

    static class ByteOrderedDataOutputStream extends FilterOutputStream {
        private final OutputStream a;
        private ByteOrder b;

        public ByteOrderedDataOutputStream(OutputStream outputStream, ByteOrder byteOrder) {
            super(outputStream);
            this.a = outputStream;
            this.b = byteOrder;
        }

        public void a(ByteOrder byteOrder) {
            this.b = byteOrder;
        }

        public void write(byte[] bArr) {
            this.a.write(bArr);
        }

        public void write(byte[] bArr, int i, int i2) {
            this.a.write(bArr, i, i2);
        }

        public void a(int i) {
            this.a.write(i);
        }

        public void a(short s) {
            if (this.b == ByteOrder.LITTLE_ENDIAN) {
                this.a.write((s >>> 0) & 255);
                this.a.write((s >>> 8) & 255);
            } else if (this.b == ByteOrder.BIG_ENDIAN) {
                this.a.write((s >>> 8) & 255);
                this.a.write((s >>> 0) & 255);
            }
        }

        public void b(int i) {
            if (this.b == ByteOrder.LITTLE_ENDIAN) {
                this.a.write((i >>> 0) & 255);
                this.a.write((i >>> 8) & 255);
                this.a.write((i >>> 16) & 255);
                this.a.write((i >>> 24) & 255);
            } else if (this.b == ByteOrder.BIG_ENDIAN) {
                this.a.write((i >>> 24) & 255);
                this.a.write((i >>> 16) & 255);
                this.a.write((i >>> 8) & 255);
                this.a.write((i >>> 0) & 255);
            }
        }

        public void c(int i) {
            a((short) i);
        }

        public void a(long j) {
            b((int) j);
        }
    }

    static class ExifAttribute {
        public final int a;
        public final int b;
        public final byte[] c;

        private ExifAttribute(int i, int i2, byte[] bArr) {
            this.a = i;
            this.b = i2;
            this.c = bArr;
        }

        public static ExifAttribute a(int[] iArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[(ExifInterface.c[3] * iArr.length)]);
            wrap.order(byteOrder);
            for (int i : iArr) {
                wrap.putShort((short) i);
            }
            return new ExifAttribute(3, iArr.length, wrap.array());
        }

        public static ExifAttribute a(int i, ByteOrder byteOrder) {
            return a(new int[]{i}, byteOrder);
        }

        public static ExifAttribute a(long[] jArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[(ExifInterface.c[4] * jArr.length)]);
            wrap.order(byteOrder);
            for (long j : jArr) {
                wrap.putInt((int) j);
            }
            return new ExifAttribute(4, jArr.length, wrap.array());
        }

        public static ExifAttribute a(long j, ByteOrder byteOrder) {
            return a(new long[]{j}, byteOrder);
        }

        public static ExifAttribute b(int[] iArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[(ExifInterface.c[9] * iArr.length)]);
            wrap.order(byteOrder);
            for (int putInt : iArr) {
                wrap.putInt(putInt);
            }
            return new ExifAttribute(9, iArr.length, wrap.array());
        }

        public static ExifAttribute a(String str) {
            if (str.length() != 1 || str.charAt(0) < '0' || str.charAt(0) > '1') {
                byte[] bytes = str.getBytes(ExifInterface.C);
                return new ExifAttribute(1, bytes.length, bytes);
            }
            byte[] bArr = {(byte) (str.charAt(0) - TarjetasConstants.ULT_NUM_AMEX)};
            return new ExifAttribute(1, bArr.length, bArr);
        }

        public static ExifAttribute b(String str) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(0);
            byte[] bytes = sb.toString().getBytes(ExifInterface.C);
            return new ExifAttribute(2, bytes.length, bytes);
        }

        public static ExifAttribute a(Rational[] rationalArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[(ExifInterface.c[5] * rationalArr.length)]);
            wrap.order(byteOrder);
            for (Rational rational : rationalArr) {
                wrap.putInt((int) rational.a);
                wrap.putInt((int) rational.b);
            }
            return new ExifAttribute(5, rationalArr.length, wrap.array());
        }

        public static ExifAttribute a(Rational rational, ByteOrder byteOrder) {
            return a(new Rational[]{rational}, byteOrder);
        }

        public static ExifAttribute b(Rational[] rationalArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[(ExifInterface.c[10] * rationalArr.length)]);
            wrap.order(byteOrder);
            for (Rational rational : rationalArr) {
                wrap.putInt((int) rational.a);
                wrap.putInt((int) rational.b);
            }
            return new ExifAttribute(10, rationalArr.length, wrap.array());
        }

        public static ExifAttribute a(double[] dArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[(ExifInterface.c[12] * dArr.length)]);
            wrap.order(byteOrder);
            for (double putDouble : dArr) {
                wrap.putDouble(putDouble);
            }
            return new ExifAttribute(12, dArr.length, wrap.array());
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            sb.append(ExifInterface.b[this.a]);
            sb.append(", data length:");
            sb.append(this.c.length);
            sb.append(")");
            return sb.toString();
        }

        /* access modifiers changed from: private */
        /* JADX WARNING: Removed duplicated region for block: B:177:0x0202 A[SYNTHETIC, Splitter:B:177:0x0202] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object d(java.nio.ByteOrder r11) {
            /*
                r10 = this;
                r0 = 0
                android.support.media.ExifInterface$ByteOrderedDataInputStream r1 = new android.support.media.ExifInterface$ByteOrderedDataInputStream     // Catch:{ IOException -> 0x01e7, all -> 0x01e4 }
                byte[] r2 = r10.c     // Catch:{ IOException -> 0x01e7, all -> 0x01e4 }
                r1.<init>(r2)     // Catch:{ IOException -> 0x01e7, all -> 0x01e4 }
                r1.a(r11)     // Catch:{ IOException -> 0x01e2 }
                int r11 = r10.a     // Catch:{ IOException -> 0x01e2 }
                r2 = 1
                r3 = 0
                switch(r11) {
                    case 1: goto L_0x018f;
                    case 2: goto L_0x0132;
                    case 3: goto L_0x0112;
                    case 4: goto L_0x00f2;
                    case 5: goto L_0x00c7;
                    case 6: goto L_0x018f;
                    case 7: goto L_0x0132;
                    case 8: goto L_0x00a7;
                    case 9: goto L_0x0087;
                    case 10: goto L_0x005a;
                    case 11: goto L_0x0039;
                    case 12: goto L_0x0019;
                    default: goto L_0x0012;
                }
            L_0x0012:
                if (r1 == 0) goto L_0x01e1
                r1.close()     // Catch:{ IOException -> 0x01d9 }
                goto L_0x01e1
            L_0x0019:
                int r11 = r10.b     // Catch:{ IOException -> 0x01e2 }
                double[] r11 = new double[r11]     // Catch:{ IOException -> 0x01e2 }
            L_0x001d:
                int r2 = r10.b     // Catch:{ IOException -> 0x01e2 }
                if (r3 >= r2) goto L_0x002a
                double r4 = r1.readDouble()     // Catch:{ IOException -> 0x01e2 }
                r11[r3] = r4     // Catch:{ IOException -> 0x01e2 }
                int r3 = r3 + 1
                goto L_0x001d
            L_0x002a:
                if (r1 == 0) goto L_0x0038
                r1.close()     // Catch:{ IOException -> 0x0030 }
                goto L_0x0038
            L_0x0030:
                r0 = move-exception
                java.lang.String r1 = "ExifInterface"
                java.lang.String r2 = "IOException occurred while closing InputStream"
                android.util.Log.e(r1, r2, r0)
            L_0x0038:
                return r11
            L_0x0039:
                int r11 = r10.b     // Catch:{ IOException -> 0x01e2 }
                double[] r11 = new double[r11]     // Catch:{ IOException -> 0x01e2 }
            L_0x003d:
                int r2 = r10.b     // Catch:{ IOException -> 0x01e2 }
                if (r3 >= r2) goto L_0x004b
                float r2 = r1.readFloat()     // Catch:{ IOException -> 0x01e2 }
                double r4 = (double) r2     // Catch:{ IOException -> 0x01e2 }
                r11[r3] = r4     // Catch:{ IOException -> 0x01e2 }
                int r3 = r3 + 1
                goto L_0x003d
            L_0x004b:
                if (r1 == 0) goto L_0x0059
                r1.close()     // Catch:{ IOException -> 0x0051 }
                goto L_0x0059
            L_0x0051:
                r0 = move-exception
                java.lang.String r1 = "ExifInterface"
                java.lang.String r2 = "IOException occurred while closing InputStream"
                android.util.Log.e(r1, r2, r0)
            L_0x0059:
                return r11
            L_0x005a:
                int r11 = r10.b     // Catch:{ IOException -> 0x01e2 }
                android.support.media.ExifInterface$Rational[] r11 = new android.support.media.ExifInterface.Rational[r11]     // Catch:{ IOException -> 0x01e2 }
            L_0x005e:
                int r2 = r10.b     // Catch:{ IOException -> 0x01e2 }
                if (r3 >= r2) goto L_0x0078
                int r2 = r1.readInt()     // Catch:{ IOException -> 0x01e2 }
                long r5 = (long) r2     // Catch:{ IOException -> 0x01e2 }
                int r2 = r1.readInt()     // Catch:{ IOException -> 0x01e2 }
                long r7 = (long) r2     // Catch:{ IOException -> 0x01e2 }
                android.support.media.ExifInterface$Rational r2 = new android.support.media.ExifInterface$Rational     // Catch:{ IOException -> 0x01e2 }
                r9 = 0
                r4 = r2
                r4.<init>(r5, r7)     // Catch:{ IOException -> 0x01e2 }
                r11[r3] = r2     // Catch:{ IOException -> 0x01e2 }
                int r3 = r3 + 1
                goto L_0x005e
            L_0x0078:
                if (r1 == 0) goto L_0x0086
                r1.close()     // Catch:{ IOException -> 0x007e }
                goto L_0x0086
            L_0x007e:
                r0 = move-exception
                java.lang.String r1 = "ExifInterface"
                java.lang.String r2 = "IOException occurred while closing InputStream"
                android.util.Log.e(r1, r2, r0)
            L_0x0086:
                return r11
            L_0x0087:
                int r11 = r10.b     // Catch:{ IOException -> 0x01e2 }
                int[] r11 = new int[r11]     // Catch:{ IOException -> 0x01e2 }
            L_0x008b:
                int r2 = r10.b     // Catch:{ IOException -> 0x01e2 }
                if (r3 >= r2) goto L_0x0098
                int r2 = r1.readInt()     // Catch:{ IOException -> 0x01e2 }
                r11[r3] = r2     // Catch:{ IOException -> 0x01e2 }
                int r3 = r3 + 1
                goto L_0x008b
            L_0x0098:
                if (r1 == 0) goto L_0x00a6
                r1.close()     // Catch:{ IOException -> 0x009e }
                goto L_0x00a6
            L_0x009e:
                r0 = move-exception
                java.lang.String r1 = "ExifInterface"
                java.lang.String r2 = "IOException occurred while closing InputStream"
                android.util.Log.e(r1, r2, r0)
            L_0x00a6:
                return r11
            L_0x00a7:
                int r11 = r10.b     // Catch:{ IOException -> 0x01e2 }
                int[] r11 = new int[r11]     // Catch:{ IOException -> 0x01e2 }
            L_0x00ab:
                int r2 = r10.b     // Catch:{ IOException -> 0x01e2 }
                if (r3 >= r2) goto L_0x00b8
                short r2 = r1.readShort()     // Catch:{ IOException -> 0x01e2 }
                r11[r3] = r2     // Catch:{ IOException -> 0x01e2 }
                int r3 = r3 + 1
                goto L_0x00ab
            L_0x00b8:
                if (r1 == 0) goto L_0x00c6
                r1.close()     // Catch:{ IOException -> 0x00be }
                goto L_0x00c6
            L_0x00be:
                r0 = move-exception
                java.lang.String r1 = "ExifInterface"
                java.lang.String r2 = "IOException occurred while closing InputStream"
                android.util.Log.e(r1, r2, r0)
            L_0x00c6:
                return r11
            L_0x00c7:
                int r11 = r10.b     // Catch:{ IOException -> 0x01e2 }
                android.support.media.ExifInterface$Rational[] r11 = new android.support.media.ExifInterface.Rational[r11]     // Catch:{ IOException -> 0x01e2 }
            L_0x00cb:
                int r2 = r10.b     // Catch:{ IOException -> 0x01e2 }
                if (r3 >= r2) goto L_0x00e3
                long r5 = r1.b()     // Catch:{ IOException -> 0x01e2 }
                long r7 = r1.b()     // Catch:{ IOException -> 0x01e2 }
                android.support.media.ExifInterface$Rational r2 = new android.support.media.ExifInterface$Rational     // Catch:{ IOException -> 0x01e2 }
                r9 = 0
                r4 = r2
                r4.<init>(r5, r7)     // Catch:{ IOException -> 0x01e2 }
                r11[r3] = r2     // Catch:{ IOException -> 0x01e2 }
                int r3 = r3 + 1
                goto L_0x00cb
            L_0x00e3:
                if (r1 == 0) goto L_0x00f1
                r1.close()     // Catch:{ IOException -> 0x00e9 }
                goto L_0x00f1
            L_0x00e9:
                r0 = move-exception
                java.lang.String r1 = "ExifInterface"
                java.lang.String r2 = "IOException occurred while closing InputStream"
                android.util.Log.e(r1, r2, r0)
            L_0x00f1:
                return r11
            L_0x00f2:
                int r11 = r10.b     // Catch:{ IOException -> 0x01e2 }
                long[] r11 = new long[r11]     // Catch:{ IOException -> 0x01e2 }
            L_0x00f6:
                int r2 = r10.b     // Catch:{ IOException -> 0x01e2 }
                if (r3 >= r2) goto L_0x0103
                long r4 = r1.b()     // Catch:{ IOException -> 0x01e2 }
                r11[r3] = r4     // Catch:{ IOException -> 0x01e2 }
                int r3 = r3 + 1
                goto L_0x00f6
            L_0x0103:
                if (r1 == 0) goto L_0x0111
                r1.close()     // Catch:{ IOException -> 0x0109 }
                goto L_0x0111
            L_0x0109:
                r0 = move-exception
                java.lang.String r1 = "ExifInterface"
                java.lang.String r2 = "IOException occurred while closing InputStream"
                android.util.Log.e(r1, r2, r0)
            L_0x0111:
                return r11
            L_0x0112:
                int r11 = r10.b     // Catch:{ IOException -> 0x01e2 }
                int[] r11 = new int[r11]     // Catch:{ IOException -> 0x01e2 }
            L_0x0116:
                int r2 = r10.b     // Catch:{ IOException -> 0x01e2 }
                if (r3 >= r2) goto L_0x0123
                int r2 = r1.readUnsignedShort()     // Catch:{ IOException -> 0x01e2 }
                r11[r3] = r2     // Catch:{ IOException -> 0x01e2 }
                int r3 = r3 + 1
                goto L_0x0116
            L_0x0123:
                if (r1 == 0) goto L_0x0131
                r1.close()     // Catch:{ IOException -> 0x0129 }
                goto L_0x0131
            L_0x0129:
                r0 = move-exception
                java.lang.String r1 = "ExifInterface"
                java.lang.String r2 = "IOException occurred while closing InputStream"
                android.util.Log.e(r1, r2, r0)
            L_0x0131:
                return r11
            L_0x0132:
                int r11 = r10.b     // Catch:{ IOException -> 0x01e2 }
                byte[] r4 = android.support.media.ExifInterface.k     // Catch:{ IOException -> 0x01e2 }
                int r4 = r4.length     // Catch:{ IOException -> 0x01e2 }
                if (r11 < r4) goto L_0x015b
                r11 = 0
            L_0x013c:
                byte[] r4 = android.support.media.ExifInterface.k     // Catch:{ IOException -> 0x01e2 }
                int r4 = r4.length     // Catch:{ IOException -> 0x01e2 }
                if (r11 >= r4) goto L_0x0154
                byte[] r4 = r10.c     // Catch:{ IOException -> 0x01e2 }
                byte r4 = r4[r11]     // Catch:{ IOException -> 0x01e2 }
                byte[] r5 = android.support.media.ExifInterface.k     // Catch:{ IOException -> 0x01e2 }
                byte r5 = r5[r11]     // Catch:{ IOException -> 0x01e2 }
                if (r4 == r5) goto L_0x0151
                r2 = 0
                goto L_0x0154
            L_0x0151:
                int r11 = r11 + 1
                goto L_0x013c
            L_0x0154:
                if (r2 == 0) goto L_0x015b
                byte[] r11 = android.support.media.ExifInterface.k     // Catch:{ IOException -> 0x01e2 }
                int r3 = r11.length     // Catch:{ IOException -> 0x01e2 }
            L_0x015b:
                java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x01e2 }
                r11.<init>()     // Catch:{ IOException -> 0x01e2 }
            L_0x0160:
                int r2 = r10.b     // Catch:{ IOException -> 0x01e2 }
                if (r3 >= r2) goto L_0x017c
                byte[] r2 = r10.c     // Catch:{ IOException -> 0x01e2 }
                byte r2 = r2[r3]     // Catch:{ IOException -> 0x01e2 }
                if (r2 != 0) goto L_0x016b
                goto L_0x017c
            L_0x016b:
                r4 = 32
                if (r2 < r4) goto L_0x0174
                char r2 = (char) r2     // Catch:{ IOException -> 0x01e2 }
                r11.append(r2)     // Catch:{ IOException -> 0x01e2 }
                goto L_0x0179
            L_0x0174:
                r2 = 63
                r11.append(r2)     // Catch:{ IOException -> 0x01e2 }
            L_0x0179:
                int r3 = r3 + 1
                goto L_0x0160
            L_0x017c:
                java.lang.String r11 = r11.toString()     // Catch:{ IOException -> 0x01e2 }
                if (r1 == 0) goto L_0x018e
                r1.close()     // Catch:{ IOException -> 0x0186 }
                goto L_0x018e
            L_0x0186:
                r0 = move-exception
                java.lang.String r1 = "ExifInterface"
                java.lang.String r2 = "IOException occurred while closing InputStream"
                android.util.Log.e(r1, r2, r0)
            L_0x018e:
                return r11
            L_0x018f:
                byte[] r11 = r10.c     // Catch:{ IOException -> 0x01e2 }
                int r11 = r11.length     // Catch:{ IOException -> 0x01e2 }
                if (r11 != r2) goto L_0x01bf
                byte[] r11 = r10.c     // Catch:{ IOException -> 0x01e2 }
                byte r11 = r11[r3]     // Catch:{ IOException -> 0x01e2 }
                if (r11 < 0) goto L_0x01bf
                byte[] r11 = r10.c     // Catch:{ IOException -> 0x01e2 }
                byte r11 = r11[r3]     // Catch:{ IOException -> 0x01e2 }
                if (r11 > r2) goto L_0x01bf
                java.lang.String r11 = new java.lang.String     // Catch:{ IOException -> 0x01e2 }
                char[] r2 = new char[r2]     // Catch:{ IOException -> 0x01e2 }
                byte[] r4 = r10.c     // Catch:{ IOException -> 0x01e2 }
                byte r4 = r4[r3]     // Catch:{ IOException -> 0x01e2 }
                int r4 = r4 + 48
                char r4 = (char) r4     // Catch:{ IOException -> 0x01e2 }
                r2[r3] = r4     // Catch:{ IOException -> 0x01e2 }
                r11.<init>(r2)     // Catch:{ IOException -> 0x01e2 }
                if (r1 == 0) goto L_0x01be
                r1.close()     // Catch:{ IOException -> 0x01b6 }
                goto L_0x01be
            L_0x01b6:
                r0 = move-exception
                java.lang.String r1 = "ExifInterface"
                java.lang.String r2 = "IOException occurred while closing InputStream"
                android.util.Log.e(r1, r2, r0)
            L_0x01be:
                return r11
            L_0x01bf:
                java.lang.String r11 = new java.lang.String     // Catch:{ IOException -> 0x01e2 }
                byte[] r2 = r10.c     // Catch:{ IOException -> 0x01e2 }
                java.nio.charset.Charset r3 = android.support.media.ExifInterface.C     // Catch:{ IOException -> 0x01e2 }
                r11.<init>(r2, r3)     // Catch:{ IOException -> 0x01e2 }
                if (r1 == 0) goto L_0x01d8
                r1.close()     // Catch:{ IOException -> 0x01d0 }
                goto L_0x01d8
            L_0x01d0:
                r0 = move-exception
                java.lang.String r1 = "ExifInterface"
                java.lang.String r2 = "IOException occurred while closing InputStream"
                android.util.Log.e(r1, r2, r0)
            L_0x01d8:
                return r11
            L_0x01d9:
                r11 = move-exception
                java.lang.String r1 = "ExifInterface"
                java.lang.String r2 = "IOException occurred while closing InputStream"
                android.util.Log.e(r1, r2, r11)
            L_0x01e1:
                return r0
            L_0x01e2:
                r11 = move-exception
                goto L_0x01e9
            L_0x01e4:
                r11 = move-exception
                r1 = r0
                goto L_0x0200
            L_0x01e7:
                r11 = move-exception
                r1 = r0
            L_0x01e9:
                java.lang.String r2 = "ExifInterface"
                java.lang.String r3 = "IOException occurred during reading a value"
                android.util.Log.w(r2, r3, r11)     // Catch:{ all -> 0x01ff }
                if (r1 == 0) goto L_0x01fe
                r1.close()     // Catch:{ IOException -> 0x01f6 }
                goto L_0x01fe
            L_0x01f6:
                r11 = move-exception
                java.lang.String r1 = "ExifInterface"
                java.lang.String r2 = "IOException occurred while closing InputStream"
                android.util.Log.e(r1, r2, r11)
            L_0x01fe:
                return r0
            L_0x01ff:
                r11 = move-exception
            L_0x0200:
                if (r1 == 0) goto L_0x020e
                r1.close()     // Catch:{ IOException -> 0x0206 }
                goto L_0x020e
            L_0x0206:
                r0 = move-exception
                java.lang.String r1 = "ExifInterface"
                java.lang.String r2 = "IOException occurred while closing InputStream"
                android.util.Log.e(r1, r2, r0)
            L_0x020e:
                throw r11
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.media.ExifInterface.ExifAttribute.d(java.nio.ByteOrder):java.lang.Object");
        }

        public double a(ByteOrder byteOrder) {
            Object d = d(byteOrder);
            if (d == null) {
                throw new NumberFormatException("NULL can't be converted to a double value");
            } else if (d instanceof String) {
                return Double.parseDouble((String) d);
            } else {
                if (d instanceof long[]) {
                    long[] jArr = (long[]) d;
                    if (jArr.length == 1) {
                        return (double) jArr[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else if (d instanceof int[]) {
                    int[] iArr = (int[]) d;
                    if (iArr.length == 1) {
                        return (double) iArr[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else if (d instanceof double[]) {
                    double[] dArr = (double[]) d;
                    if (dArr.length == 1) {
                        return dArr[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else if (d instanceof Rational[]) {
                    Rational[] rationalArr = (Rational[]) d;
                    if (rationalArr.length == 1) {
                        return rationalArr[0].a();
                    }
                    throw new NumberFormatException("There are more than one component");
                } else {
                    throw new NumberFormatException("Couldn't find a double value");
                }
            }
        }

        public int b(ByteOrder byteOrder) {
            Object d = d(byteOrder);
            if (d == null) {
                throw new NumberFormatException("NULL can't be converted to a integer value");
            } else if (d instanceof String) {
                return Integer.parseInt((String) d);
            } else {
                if (d instanceof long[]) {
                    long[] jArr = (long[]) d;
                    if (jArr.length == 1) {
                        return (int) jArr[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else if (d instanceof int[]) {
                    int[] iArr = (int[]) d;
                    if (iArr.length == 1) {
                        return iArr[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else {
                    throw new NumberFormatException("Couldn't find a integer value");
                }
            }
        }

        public String c(ByteOrder byteOrder) {
            Object d = d(byteOrder);
            if (d == null) {
                return null;
            }
            if (d instanceof String) {
                return (String) d;
            }
            StringBuilder sb = new StringBuilder();
            int i = 0;
            if (d instanceof long[]) {
                long[] jArr = (long[]) d;
                while (i < jArr.length) {
                    sb.append(jArr[i]);
                    i++;
                    if (i != jArr.length) {
                        sb.append(",");
                    }
                }
                return sb.toString();
            } else if (d instanceof int[]) {
                int[] iArr = (int[]) d;
                while (i < iArr.length) {
                    sb.append(iArr[i]);
                    i++;
                    if (i != iArr.length) {
                        sb.append(",");
                    }
                }
                return sb.toString();
            } else if (d instanceof double[]) {
                double[] dArr = (double[]) d;
                while (i < dArr.length) {
                    sb.append(dArr[i]);
                    i++;
                    if (i != dArr.length) {
                        sb.append(",");
                    }
                }
                return sb.toString();
            } else if (!(d instanceof Rational[])) {
                return null;
            } else {
                Rational[] rationalArr = (Rational[]) d;
                while (i < rationalArr.length) {
                    sb.append(rationalArr[i].a);
                    sb.append('/');
                    sb.append(rationalArr[i].b);
                    i++;
                    if (i != rationalArr.length) {
                        sb.append(",");
                    }
                }
                return sb.toString();
            }
        }

        public int a() {
            return ExifInterface.c[this.a] * this.b;
        }
    }

    static class ExifTag {
        public final int a;
        public final String b;
        public final int c;
        public final int d;

        private ExifTag(String str, int i, int i2) {
            this.b = str;
            this.a = i;
            this.c = i2;
            this.d = -1;
        }

        private ExifTag(String str, int i, int i2, int i3) {
            this.b = str;
            this.a = i;
            this.c = i2;
            this.d = i3;
        }

        /* access modifiers changed from: private */
        public boolean a(int i) {
            if (this.c == 7 || i == 7 || this.c == i || this.d == i) {
                return true;
            }
            if ((this.c == 4 || this.d == 4) && i == 3) {
                return true;
            }
            if ((this.c == 9 || this.d == 9) && i == 8) {
                return true;
            }
            if ((this.c == 12 || this.d == 12) && i == 11) {
                return true;
            }
            return false;
        }
    }

    @RestrictTo({Scope.LIBRARY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IfdType {
    }

    static class Rational {
        public final long a;
        public final long b;

        private Rational(double d) {
            this((long) (d * 10000.0d), (long) LocalizacionManager.UPDATE_INTERVAL_IN_MILLISECONDS);
        }

        private Rational(long j, long j2) {
            if (j2 == 0) {
                this.a = 0;
                this.b = 1;
                return;
            }
            this.a = j;
            this.b = j2;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.a);
            sb.append("/");
            sb.append(this.b);
            return sb.toString();
        }

        public double a() {
            return ((double) this.a) / ((double) this.b);
        }
    }

    static {
        ExifTag[] exifTagArr;
        ExifTag exifTag = new ExifTag(TAG_IMAGE_WIDTH, 256, 3, 4);
        ExifTag exifTag2 = new ExifTag(TAG_IMAGE_LENGTH, 257, 3, 4);
        ExifTag exifTag3 = new ExifTag(TAG_STRIP_OFFSETS, 273, 3, 4);
        ExifTag exifTag4 = new ExifTag(TAG_ROWS_PER_STRIP, 278, 3, 4);
        ExifTag exifTag5 = new ExifTag(TAG_STRIP_BYTE_COUNTS, 279, 3, 4);
        l = new ExifTag[]{new ExifTag(TAG_NEW_SUBFILE_TYPE, 254, 4), new ExifTag(TAG_SUBFILE_TYPE, 255, 4), exifTag, exifTag2, new ExifTag(TAG_BITS_PER_SAMPLE, (int) BZip2Constants.MAX_ALPHA_SIZE, 3), new ExifTag(TAG_COMPRESSION, 259, 3), new ExifTag(TAG_PHOTOMETRIC_INTERPRETATION, 262, 3), new ExifTag(TAG_IMAGE_DESCRIPTION, (int) SubsamplingScaleImageView.ORIENTATION_270, 2), new ExifTag(TAG_MAKE, 271, 2), new ExifTag(TAG_MODEL, 272, 2), exifTag3, new ExifTag(TAG_ORIENTATION, 274, 3), new ExifTag(TAG_SAMPLES_PER_PIXEL, 277, 3), exifTag4, exifTag5, new ExifTag(TAG_X_RESOLUTION, 282, 5), new ExifTag(TAG_Y_RESOLUTION, 283, 5), new ExifTag(TAG_PLANAR_CONFIGURATION, 284, 3), new ExifTag(TAG_RESOLUTION_UNIT, 296, 3), new ExifTag(TAG_TRANSFER_FUNCTION, (int) HttpStatus.SC_MOVED_PERMANENTLY, 3), new ExifTag(TAG_SOFTWARE, (int) HttpStatus.SC_USE_PROXY, 2), new ExifTag(TAG_DATETIME, 306, 2), new ExifTag(TAG_ARTIST, 315, 2), new ExifTag(TAG_WHITE_POINT, 318, 5), new ExifTag(TAG_PRIMARY_CHROMATICITIES, 319, 5), new ExifTag("SubIFDPointer", 330, 4), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT, (int) InputDeviceCompat.SOURCE_DPAD, 4), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, 4), new ExifTag(TAG_Y_CB_CR_COEFFICIENTS, 529, 5), new ExifTag(TAG_Y_CB_CR_SUB_SAMPLING, 530, 3), new ExifTag(TAG_Y_CB_CR_POSITIONING, 531, 3), new ExifTag(TAG_REFERENCE_BLACK_WHITE, 532, 5), new ExifTag(TAG_COPYRIGHT, 33432, 2), new ExifTag("ExifIFDPointer", 34665, 4), new ExifTag("GPSInfoIFDPointer", 34853, 4), new ExifTag(TAG_RW2_SENSOR_TOP_BORDER, 4, 4), new ExifTag(TAG_RW2_SENSOR_LEFT_BORDER, 5, 4), new ExifTag(TAG_RW2_SENSOR_BOTTOM_BORDER, 6, 4), new ExifTag(TAG_RW2_SENSOR_RIGHT_BORDER, 7, 4), new ExifTag(TAG_RW2_ISO, 23, 3), new ExifTag(TAG_RW2_JPG_FROM_RAW, 46, 7)};
        ExifTag exifTag6 = new ExifTag(TAG_PIXEL_X_DIMENSION, 40962, 3, 4);
        ExifTag exifTag7 = new ExifTag(TAG_PIXEL_Y_DIMENSION, 40963, 3, 4);
        ExifTag exifTag8 = new ExifTag(TAG_DEFAULT_CROP_SIZE, 50720, 3, 4);
        m = new ExifTag[]{new ExifTag(TAG_EXPOSURE_TIME, 33434, 5), new ExifTag(TAG_F_NUMBER, 33437, 5), new ExifTag(TAG_EXPOSURE_PROGRAM, 34850, 3), new ExifTag(TAG_SPECTRAL_SENSITIVITY, 34852, 2), new ExifTag(TAG_PHOTOGRAPHIC_SENSITIVITY, 34855, 3), new ExifTag(TAG_OECF, 34856, 7), new ExifTag(TAG_EXIF_VERSION, 36864, 2), new ExifTag(TAG_DATETIME_ORIGINAL, 36867, 2), new ExifTag(TAG_DATETIME_DIGITIZED, 36868, 2), new ExifTag(TAG_COMPONENTS_CONFIGURATION, 37121, 7), new ExifTag(TAG_COMPRESSED_BITS_PER_PIXEL, 37122, 5), new ExifTag(TAG_SHUTTER_SPEED_VALUE, 37377, 10), new ExifTag(TAG_APERTURE_VALUE, 37378, 5), new ExifTag(TAG_BRIGHTNESS_VALUE, 37379, 10), new ExifTag(TAG_EXPOSURE_BIAS_VALUE, 37380, 10), new ExifTag(TAG_MAX_APERTURE_VALUE, 37381, 5), new ExifTag(TAG_SUBJECT_DISTANCE, 37382, 5), new ExifTag(TAG_METERING_MODE, 37383, 3), new ExifTag(TAG_LIGHT_SOURCE, 37384, 3), new ExifTag(TAG_FLASH, 37385, 3), new ExifTag(TAG_FOCAL_LENGTH, 37386, 5), new ExifTag(TAG_SUBJECT_AREA, 37396, 3), new ExifTag(TAG_MAKER_NOTE, 37500, 7), new ExifTag(TAG_USER_COMMENT, 37510, 7), new ExifTag(TAG_SUBSEC_TIME, 37520, 2), new ExifTag(TAG_SUBSEC_TIME_ORIGINAL, 37521, 2), new ExifTag(TAG_SUBSEC_TIME_DIGITIZED, 37522, 2), new ExifTag(TAG_FLASHPIX_VERSION, 40960, 7), new ExifTag(TAG_COLOR_SPACE, 40961, 3), exifTag6, exifTag7, new ExifTag(TAG_RELATED_SOUND_FILE, 40964, 2), new ExifTag("InteroperabilityIFDPointer", 40965, 4), new ExifTag(TAG_FLASH_ENERGY, 41483, 5), new ExifTag(TAG_SPATIAL_FREQUENCY_RESPONSE, 41484, 7), new ExifTag(TAG_FOCAL_PLANE_X_RESOLUTION, 41486, 5), new ExifTag(TAG_FOCAL_PLANE_Y_RESOLUTION, 41487, 5), new ExifTag(TAG_FOCAL_PLANE_RESOLUTION_UNIT, 41488, 3), new ExifTag(TAG_SUBJECT_LOCATION, 41492, 3), new ExifTag(TAG_EXPOSURE_INDEX, 41493, 5), new ExifTag(TAG_SENSING_METHOD, 41495, 3), new ExifTag(TAG_FILE_SOURCE, 41728, 7), new ExifTag(TAG_SCENE_TYPE, 41729, 7), new ExifTag(TAG_CFA_PATTERN, 41730, 7), new ExifTag(TAG_CUSTOM_RENDERED, 41985, 3), new ExifTag(TAG_EXPOSURE_MODE, 41986, 3), new ExifTag(TAG_WHITE_BALANCE, 41987, 3), new ExifTag(TAG_DIGITAL_ZOOM_RATIO, 41988, 5), new ExifTag(TAG_FOCAL_LENGTH_IN_35MM_FILM, 41989, 3), new ExifTag(TAG_SCENE_CAPTURE_TYPE, 41990, 3), new ExifTag(TAG_GAIN_CONTROL, 41991, 3), new ExifTag(TAG_CONTRAST, 41992, 3), new ExifTag(TAG_SATURATION, 41993, 3), new ExifTag(TAG_SHARPNESS, 41994, 3), new ExifTag(TAG_DEVICE_SETTING_DESCRIPTION, 41995, 7), new ExifTag(TAG_SUBJECT_DISTANCE_RANGE, 41996, 3), new ExifTag(TAG_IMAGE_UNIQUE_ID, 42016, 2), new ExifTag(TAG_DNG_VERSION, 50706, 1), exifTag8};
        ExifTag exifTag9 = new ExifTag(TAG_THUMBNAIL_IMAGE_WIDTH, 256, 3, 4);
        ExifTag exifTag10 = new ExifTag(TAG_THUMBNAIL_IMAGE_LENGTH, 257, 3, 4);
        ExifTag exifTag11 = new ExifTag(TAG_STRIP_OFFSETS, 273, 3, 4);
        ExifTag exifTag12 = new ExifTag(TAG_ROWS_PER_STRIP, 278, 3, 4);
        ExifTag exifTag13 = new ExifTag(TAG_STRIP_BYTE_COUNTS, 279, 3, 4);
        ExifTag exifTag14 = new ExifTag(TAG_DEFAULT_CROP_SIZE, 50720, 3, 4);
        p = new ExifTag[]{new ExifTag(TAG_NEW_SUBFILE_TYPE, 254, 4), new ExifTag(TAG_SUBFILE_TYPE, 255, 4), exifTag9, exifTag10, new ExifTag(TAG_BITS_PER_SAMPLE, (int) BZip2Constants.MAX_ALPHA_SIZE, 3), new ExifTag(TAG_COMPRESSION, 259, 3), new ExifTag(TAG_PHOTOMETRIC_INTERPRETATION, 262, 3), new ExifTag(TAG_IMAGE_DESCRIPTION, (int) SubsamplingScaleImageView.ORIENTATION_270, 2), new ExifTag(TAG_MAKE, 271, 2), new ExifTag(TAG_MODEL, 272, 2), exifTag11, new ExifTag(TAG_ORIENTATION, 274, 3), new ExifTag(TAG_SAMPLES_PER_PIXEL, 277, 3), exifTag12, exifTag13, new ExifTag(TAG_X_RESOLUTION, 282, 5), new ExifTag(TAG_Y_RESOLUTION, 283, 5), new ExifTag(TAG_PLANAR_CONFIGURATION, 284, 3), new ExifTag(TAG_RESOLUTION_UNIT, 296, 3), new ExifTag(TAG_TRANSFER_FUNCTION, (int) HttpStatus.SC_MOVED_PERMANENTLY, 3), new ExifTag(TAG_SOFTWARE, (int) HttpStatus.SC_USE_PROXY, 2), new ExifTag(TAG_DATETIME, 306, 2), new ExifTag(TAG_ARTIST, 315, 2), new ExifTag(TAG_WHITE_POINT, 318, 5), new ExifTag(TAG_PRIMARY_CHROMATICITIES, 319, 5), new ExifTag("SubIFDPointer", 330, 4), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT, (int) InputDeviceCompat.SOURCE_DPAD, 4), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, 4), new ExifTag(TAG_Y_CB_CR_COEFFICIENTS, 529, 5), new ExifTag(TAG_Y_CB_CR_SUB_SAMPLING, 530, 3), new ExifTag(TAG_Y_CB_CR_POSITIONING, 531, 3), new ExifTag(TAG_REFERENCE_BLACK_WHITE, 532, 5), new ExifTag(TAG_COPYRIGHT, 33432, 2), new ExifTag("ExifIFDPointer", 34665, 4), new ExifTag("GPSInfoIFDPointer", 34853, 4), new ExifTag(TAG_DNG_VERSION, 50706, 1), exifTag14};
        j.setTimeZone(TimeZone.getTimeZone("UTC"));
        for (int i2 = 0; i2 < d.length; i2++) {
            y[i2] = new HashMap<>();
            z[i2] = new HashMap<>();
            for (ExifTag exifTag15 : d[i2]) {
                y[i2].put(Integer.valueOf(exifTag15.a), exifTag15);
                z[i2].put(exifTag15.b, exifTag15);
            }
        }
        B.put(Integer.valueOf(v[0].a), Integer.valueOf(5));
        B.put(Integer.valueOf(v[1].a), Integer.valueOf(1));
        B.put(Integer.valueOf(v[2].a), Integer.valueOf(2));
        B.put(Integer.valueOf(v[3].a), Integer.valueOf(3));
        B.put(Integer.valueOf(v[4].a), Integer.valueOf(7));
        B.put(Integer.valueOf(v[5].a), Integer.valueOf(8));
    }

    public ExifInterface(@NonNull String str) {
        if (str == null) {
            throw new IllegalArgumentException("filename cannot be null");
        }
        FileInputStream fileInputStream = null;
        this.E = null;
        this.D = str;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(str);
            try {
                a((InputStream) fileInputStream2);
                a((Closeable) fileInputStream2);
            } catch (Throwable th) {
                th = th;
                fileInputStream = fileInputStream2;
                a((Closeable) fileInputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            a((Closeable) fileInputStream);
            throw th;
        }
    }

    public ExifInterface(@NonNull InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("inputStream cannot be null");
        }
        this.D = null;
        if (inputStream instanceof AssetInputStream) {
            this.E = (AssetInputStream) inputStream;
        } else {
            this.E = null;
        }
        a(inputStream);
    }

    @Nullable
    private ExifAttribute a(@NonNull String str) {
        if (TAG_ISO_SPEED_RATINGS.equals(str)) {
            str = TAG_PHOTOGRAPHIC_SENSITIVITY;
        }
        for (int i2 = 0; i2 < d.length; i2++) {
            ExifAttribute exifAttribute = (ExifAttribute) this.G[i2].get(str);
            if (exifAttribute != null) {
                return exifAttribute;
            }
        }
        return null;
    }

    @Nullable
    public String getAttribute(@NonNull String str) {
        ExifAttribute a2 = a(str);
        if (a2 == null) {
            return null;
        }
        if (!A.contains(str)) {
            return a2.c(this.H);
        }
        if (!str.equals(TAG_GPS_TIMESTAMP)) {
            try {
                return Double.toString(a2.a(this.H));
            } catch (NumberFormatException unused) {
                return null;
            }
        } else if (a2.a == 5 || a2.a == 10) {
            Rational[] rationalArr = (Rational[]) a2.d(this.H);
            if (rationalArr == null || rationalArr.length != 3) {
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid GPS Timestamp array. array=");
                sb.append(Arrays.toString(rationalArr));
                Log.w("ExifInterface", sb.toString());
                return null;
            }
            return String.format("%02d:%02d:%02d", new Object[]{Integer.valueOf((int) (((float) rationalArr[0].a) / ((float) rationalArr[0].b))), Integer.valueOf((int) (((float) rationalArr[1].a) / ((float) rationalArr[1].b))), Integer.valueOf((int) (((float) rationalArr[2].a) / ((float) rationalArr[2].b)))});
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("GPS Timestamp format is not rational. format=");
            sb2.append(a2.a);
            Log.w("ExifInterface", sb2.toString());
            return null;
        }
    }

    public int getAttributeInt(@NonNull String str, int i2) {
        ExifAttribute a2 = a(str);
        if (a2 == null) {
            return i2;
        }
        try {
            return a2.b(this.H);
        } catch (NumberFormatException unused) {
            return i2;
        }
    }

    public double getAttributeDouble(@NonNull String str, double d2) {
        ExifAttribute a2 = a(str);
        if (a2 == null) {
            return d2;
        }
        try {
            return a2.a(this.H);
        } catch (NumberFormatException unused) {
            return d2;
        }
    }

    public void setAttribute(@NonNull String str, @Nullable String str2) {
        int i2;
        String str3;
        String str4;
        String str5 = str2;
        String str6 = str;
        String str7 = TAG_ISO_SPEED_RATINGS.equals(str6) ? TAG_PHOTOGRAPHIC_SENSITIVITY : str6;
        if (str5 != null && A.contains(str7)) {
            if (str7.equals(TAG_GPS_TIMESTAMP)) {
                Matcher matcher = U.matcher(str5);
                if (!matcher.find()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Invalid value for ");
                    sb.append(str7);
                    sb.append(" : ");
                    sb.append(str5);
                    Log.w("ExifInterface", sb.toString());
                    return;
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Integer.parseInt(matcher.group(1)));
                sb2.append("/1,");
                sb2.append(Integer.parseInt(matcher.group(2)));
                sb2.append("/1,");
                sb2.append(Integer.parseInt(matcher.group(3)));
                sb2.append("/1");
                str5 = sb2.toString();
            } else {
                try {
                    str5 = new Rational(Double.parseDouble(str2)).toString();
                } catch (NumberFormatException unused) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Invalid value for ");
                    sb3.append(str7);
                    sb3.append(" : ");
                    sb3.append(str5);
                    Log.w("ExifInterface", sb3.toString());
                    return;
                }
            }
        }
        for (int i3 = 0; i3 < d.length; i3++) {
            if (i3 != 4 || this.I) {
                ExifTag exifTag = (ExifTag) z[i3].get(str7);
                if (exifTag != null) {
                    if (str5 != null) {
                        Pair c2 = c(str5);
                        if (exifTag.c == ((Integer) c2.first).intValue() || exifTag.c == ((Integer) c2.second).intValue()) {
                            i2 = exifTag.c;
                        } else if (exifTag.d != -1 && (exifTag.d == ((Integer) c2.first).intValue() || exifTag.d == ((Integer) c2.second).intValue())) {
                            i2 = exifTag.d;
                        } else if (exifTag.c == 1 || exifTag.c == 7 || exifTag.c == 2) {
                            i2 = exifTag.c;
                        } else {
                            String str8 = "ExifInterface";
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append("Given tag (");
                            sb4.append(str7);
                            sb4.append(") value didn't match with one of expected ");
                            sb4.append("formats: ");
                            sb4.append(b[exifTag.c]);
                            if (exifTag.d == -1) {
                                str3 = "";
                            } else {
                                StringBuilder sb5 = new StringBuilder();
                                sb5.append(", ");
                                sb5.append(b[exifTag.d]);
                                str3 = sb5.toString();
                            }
                            sb4.append(str3);
                            sb4.append(" (guess: ");
                            sb4.append(b[((Integer) c2.first).intValue()]);
                            if (((Integer) c2.second).intValue() == -1) {
                                str4 = "";
                            } else {
                                StringBuilder sb6 = new StringBuilder();
                                sb6.append(", ");
                                sb6.append(b[((Integer) c2.second).intValue()]);
                                str4 = sb6.toString();
                            }
                            sb4.append(str4);
                            sb4.append(")");
                            Log.w(str8, sb4.toString());
                        }
                        switch (i2) {
                            case 1:
                                this.G[i3].put(str7, ExifAttribute.a(str5));
                                break;
                            case 2:
                            case 7:
                                this.G[i3].put(str7, ExifAttribute.b(str5));
                                break;
                            case 3:
                                String[] split = str5.split(",");
                                int[] iArr = new int[split.length];
                                for (int i4 = 0; i4 < split.length; i4++) {
                                    iArr[i4] = Integer.parseInt(split[i4]);
                                }
                                this.G[i3].put(str7, ExifAttribute.a(iArr, this.H));
                                break;
                            case 4:
                                String[] split2 = str5.split(",");
                                long[] jArr = new long[split2.length];
                                for (int i5 = 0; i5 < split2.length; i5++) {
                                    jArr[i5] = Long.parseLong(split2[i5]);
                                }
                                this.G[i3].put(str7, ExifAttribute.a(jArr, this.H));
                                break;
                            case 5:
                                String[] split3 = str5.split(",");
                                Rational[] rationalArr = new Rational[split3.length];
                                for (int i6 = 0; i6 < split3.length; i6++) {
                                    String[] split4 = split3[i6].split("/");
                                    Rational rational = new Rational((long) Double.parseDouble(split4[0]), (long) Double.parseDouble(split4[1]));
                                    rationalArr[i6] = rational;
                                }
                                this.G[i3].put(str7, ExifAttribute.a(rationalArr, this.H));
                                break;
                            case 9:
                                String[] split5 = str5.split(",");
                                int[] iArr2 = new int[split5.length];
                                for (int i7 = 0; i7 < split5.length; i7++) {
                                    iArr2[i7] = Integer.parseInt(split5[i7]);
                                }
                                this.G[i3].put(str7, ExifAttribute.b(iArr2, this.H));
                                break;
                            case 10:
                                String[] split6 = str5.split(",");
                                Rational[] rationalArr2 = new Rational[split6.length];
                                for (int i8 = 0; i8 < split6.length; i8++) {
                                    String[] split7 = split6[i8].split("/");
                                    Rational rational2 = new Rational((long) Double.parseDouble(split7[0]), (long) Double.parseDouble(split7[1]));
                                    rationalArr2[i8] = rational2;
                                }
                                this.G[i3].put(str7, ExifAttribute.b(rationalArr2, this.H));
                                break;
                            case 12:
                                String[] split8 = str5.split(",");
                                double[] dArr = new double[split8.length];
                                for (int i9 = 0; i9 < split8.length; i9++) {
                                    dArr[i9] = Double.parseDouble(split8[i9]);
                                }
                                this.G[i3].put(str7, ExifAttribute.a(dArr, this.H));
                                break;
                            default:
                                StringBuilder sb7 = new StringBuilder();
                                sb7.append("Data format isn't one of expected formats: ");
                                sb7.append(i2);
                                Log.w("ExifInterface", sb7.toString());
                                break;
                        }
                    } else {
                        this.G[i3].remove(str7);
                    }
                }
            }
        }
    }

    public void resetOrientation() {
        setAttribute(TAG_ORIENTATION, Integer.toString(1));
    }

    public void rotate(int i2) {
        if (i2 % 90 != 0) {
            throw new IllegalArgumentException("degree should be a multiple of 90");
        }
        int attributeInt = getAttributeInt(TAG_ORIENTATION, 1);
        int i3 = 0;
        if (f.contains(Integer.valueOf(attributeInt))) {
            int indexOf = (f.indexOf(Integer.valueOf(attributeInt)) + (i2 / 90)) % 4;
            if (indexOf < 0) {
                i3 = 4;
            }
            i3 = ((Integer) f.get(indexOf + i3)).intValue();
        } else if (g.contains(Integer.valueOf(attributeInt))) {
            int indexOf2 = (g.indexOf(Integer.valueOf(attributeInt)) + (i2 / 90)) % 4;
            if (indexOf2 < 0) {
                i3 = 4;
            }
            i3 = ((Integer) g.get(indexOf2 + i3)).intValue();
        }
        setAttribute(TAG_ORIENTATION, Integer.toString(i3));
    }

    public void flipVertically() {
        int i2 = 1;
        switch (getAttributeInt(TAG_ORIENTATION, 1)) {
            case 1:
                i2 = 4;
                break;
            case 2:
                i2 = 3;
                break;
            case 3:
                i2 = 2;
                break;
            case 4:
                break;
            case 5:
                i2 = 8;
                break;
            case 6:
                i2 = 7;
                break;
            case 7:
                i2 = 6;
                break;
            case 8:
                i2 = 5;
                break;
            default:
                i2 = 0;
                break;
        }
        setAttribute(TAG_ORIENTATION, Integer.toString(i2));
    }

    public void flipHorizontally() {
        int i2 = 1;
        switch (getAttributeInt(TAG_ORIENTATION, 1)) {
            case 1:
                i2 = 2;
                break;
            case 2:
                break;
            case 3:
                i2 = 4;
                break;
            case 4:
                i2 = 3;
                break;
            case 5:
                i2 = 6;
                break;
            case 6:
                i2 = 5;
                break;
            case 7:
                i2 = 8;
                break;
            case 8:
                i2 = 7;
                break;
            default:
                i2 = 0;
                break;
        }
        setAttribute(TAG_ORIENTATION, Integer.toString(i2));
    }

    public boolean isFlipped() {
        int attributeInt = getAttributeInt(TAG_ORIENTATION, 1);
        if (!(attributeInt == 2 || attributeInt == 7)) {
            switch (attributeInt) {
                case 4:
                case 5:
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    public int getRotationDegrees() {
        switch (getAttributeInt(TAG_ORIENTATION, 1)) {
            case 3:
            case 4:
                return 180;
            case 5:
            case 8:
                return SubsamplingScaleImageView.ORIENTATION_270;
            case 6:
            case 7:
                return 90;
            default:
                return 0;
        }
    }

    private void b(String str) {
        for (int i2 = 0; i2 < d.length; i2++) {
            this.G[i2].remove(str);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0048, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r4.S = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0050, code lost:
        c();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0053, code lost:
        throw r5;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x004a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(@android.support.annotation.NonNull java.io.InputStream r5) {
        /*
            r4 = this;
            r0 = 0
            r1 = 0
        L_0x0002:
            android.support.media.ExifInterface$ExifTag[][] r2 = d     // Catch:{ IOException -> 0x004a }
            int r2 = r2.length     // Catch:{ IOException -> 0x004a }
            if (r1 >= r2) goto L_0x0013
            java.util.HashMap<java.lang.String, android.support.media.ExifInterface$ExifAttribute>[] r2 = r4.G     // Catch:{ IOException -> 0x004a }
            java.util.HashMap r3 = new java.util.HashMap     // Catch:{ IOException -> 0x004a }
            r3.<init>()     // Catch:{ IOException -> 0x004a }
            r2[r1] = r3     // Catch:{ IOException -> 0x004a }
            int r1 = r1 + 1
            goto L_0x0002
        L_0x0013:
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x004a }
            r2 = 5000(0x1388, float:7.006E-42)
            r1.<init>(r5, r2)     // Catch:{ IOException -> 0x004a }
            r5 = r1
            java.io.BufferedInputStream r5 = (java.io.BufferedInputStream) r5     // Catch:{ IOException -> 0x004a }
            int r5 = r4.a(r5)     // Catch:{ IOException -> 0x004a }
            r4.F = r5     // Catch:{ IOException -> 0x004a }
            android.support.media.ExifInterface$ByteOrderedDataInputStream r5 = new android.support.media.ExifInterface$ByteOrderedDataInputStream     // Catch:{ IOException -> 0x004a }
            r5.<init>(r1)     // Catch:{ IOException -> 0x004a }
            int r1 = r4.F     // Catch:{ IOException -> 0x004a }
            switch(r1) {
                case 0: goto L_0x003e;
                case 1: goto L_0x003e;
                case 2: goto L_0x003e;
                case 3: goto L_0x003e;
                case 4: goto L_0x003a;
                case 5: goto L_0x003e;
                case 6: goto L_0x003e;
                case 7: goto L_0x0036;
                case 8: goto L_0x003e;
                case 9: goto L_0x0032;
                case 10: goto L_0x002e;
                case 11: goto L_0x003e;
                default: goto L_0x002d;
            }     // Catch:{ IOException -> 0x004a }
        L_0x002d:
            goto L_0x0041
        L_0x002e:
            r4.d(r5)     // Catch:{ IOException -> 0x004a }
            goto L_0x0041
        L_0x0032:
            r4.b(r5)     // Catch:{ IOException -> 0x004a }
            goto L_0x0041
        L_0x0036:
            r4.c(r5)     // Catch:{ IOException -> 0x004a }
            goto L_0x0041
        L_0x003a:
            r4.a(r5, r0, r0)     // Catch:{ IOException -> 0x004a }
            goto L_0x0041
        L_0x003e:
            r4.a(r5)     // Catch:{ IOException -> 0x004a }
        L_0x0041:
            r4.f(r5)     // Catch:{ IOException -> 0x004a }
            r5 = 1
            r4.S = r5     // Catch:{ IOException -> 0x004a }
            goto L_0x004c
        L_0x0048:
            r5 = move-exception
            goto L_0x0050
        L_0x004a:
            r4.S = r0     // Catch:{ all -> 0x0048 }
        L_0x004c:
            r4.c()
            return
        L_0x0050:
            r4.c()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.media.ExifInterface.a(java.io.InputStream):void");
    }

    public void saveAttributes() {
        Throwable th;
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        if (!this.S || this.F != 4) {
            throw new IOException("ExifInterface only supports saving attributes on JPEG formats.");
        } else if (this.D == null) {
            throw new IOException("ExifInterface does not support saving attributes for the current input.");
        } else {
            this.L = getThumbnail();
            StringBuilder sb = new StringBuilder();
            sb.append(this.D);
            sb.append(".tmp");
            File file = new File(sb.toString());
            if (!new File(this.D).renameTo(file)) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Could not rename to ");
                sb2.append(file.getAbsolutePath());
                throw new IOException(sb2.toString());
            }
            FileOutputStream fileOutputStream2 = null;
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    fileOutputStream = new FileOutputStream(this.D);
                } catch (Throwable th2) {
                    th = th2;
                    a((Closeable) fileInputStream);
                    a((Closeable) fileOutputStream2);
                    file.delete();
                    throw th;
                }
                try {
                    a((InputStream) fileInputStream, (OutputStream) fileOutputStream);
                    a((Closeable) fileInputStream);
                    a((Closeable) fileOutputStream);
                    file.delete();
                    this.L = null;
                } catch (Throwable th3) {
                    FileOutputStream fileOutputStream3 = fileOutputStream;
                    th = th3;
                    fileOutputStream2 = fileOutputStream3;
                    a((Closeable) fileInputStream);
                    a((Closeable) fileOutputStream2);
                    file.delete();
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                fileInputStream = null;
                a((Closeable) fileInputStream);
                a((Closeable) fileOutputStream2);
                file.delete();
                throw th;
            }
        }
    }

    public boolean hasThumbnail() {
        return this.I;
    }

    @Nullable
    public byte[] getThumbnail() {
        if (this.M == 6 || this.M == 7) {
            return getThumbnailBytes();
        }
        return null;
    }

    @Nullable
    public byte[] getThumbnailBytes() {
        InputStream inputStream;
        if (!this.I) {
            return null;
        }
        if (this.L != null) {
            return this.L;
        }
        try {
            if (this.E != null) {
                inputStream = this.E;
                try {
                    if (inputStream.markSupported()) {
                        inputStream.reset();
                    } else {
                        Log.d("ExifInterface", "Cannot read thumbnail from inputstream without mark/reset support");
                        a((Closeable) inputStream);
                        return null;
                    }
                } catch (IOException e2) {
                    e = e2;
                    try {
                        Log.d("ExifInterface", "Encountered exception while getting thumbnail", e);
                        a((Closeable) inputStream);
                        return null;
                    } catch (Throwable th) {
                        th = th;
                        a((Closeable) inputStream);
                        throw th;
                    }
                }
            } else {
                inputStream = this.D != null ? new FileInputStream(this.D) : null;
            }
            if (inputStream == null) {
                throw new FileNotFoundException();
            } else if (inputStream.skip((long) this.J) != ((long) this.J)) {
                throw new IOException("Corrupted image");
            } else {
                byte[] bArr = new byte[this.K];
                if (inputStream.read(bArr) != this.K) {
                    throw new IOException("Corrupted image");
                }
                this.L = bArr;
                a((Closeable) inputStream);
                return bArr;
            }
        } catch (IOException e3) {
            e = e3;
            inputStream = null;
            Log.d("ExifInterface", "Encountered exception while getting thumbnail", e);
            a((Closeable) inputStream);
            return null;
        } catch (Throwable th2) {
            th = th2;
            inputStream = null;
            a((Closeable) inputStream);
            throw th;
        }
    }

    @Nullable
    public Bitmap getThumbnailBitmap() {
        if (!this.I) {
            return null;
        }
        if (this.L == null) {
            this.L = getThumbnailBytes();
        }
        if (this.M == 6 || this.M == 7) {
            return BitmapFactory.decodeByteArray(this.L, 0, this.K);
        }
        if (this.M == 1) {
            int[] iArr = new int[(this.L.length / 3)];
            for (int i2 = 0; i2 < iArr.length; i2++) {
                int i3 = i2 * 3;
                iArr[i2] = (this.L[i3] << Ascii.DLE) + 0 + (this.L[i3 + 1] << 8) + this.L[i3 + 2];
            }
            ExifAttribute exifAttribute = (ExifAttribute) this.G[4].get(TAG_IMAGE_LENGTH);
            ExifAttribute exifAttribute2 = (ExifAttribute) this.G[4].get(TAG_IMAGE_WIDTH);
            if (!(exifAttribute == null || exifAttribute2 == null)) {
                return Bitmap.createBitmap(iArr, exifAttribute2.b(this.H), exifAttribute.b(this.H), Config.ARGB_8888);
            }
        }
        return null;
    }

    public boolean isThumbnailCompressed() {
        return this.M == 6 || this.M == 7;
    }

    @Nullable
    public long[] getThumbnailRange() {
        if (!this.I) {
            return null;
        }
        return new long[]{(long) this.J, (long) this.K};
    }

    @Deprecated
    public boolean getLatLong(float[] fArr) {
        double[] latLong = getLatLong();
        if (latLong == null) {
            return false;
        }
        fArr[0] = (float) latLong[0];
        fArr[1] = (float) latLong[1];
        return true;
    }

    @Nullable
    public double[] getLatLong() {
        String attribute = getAttribute(TAG_GPS_LATITUDE);
        String attribute2 = getAttribute(TAG_GPS_LATITUDE_REF);
        String attribute3 = getAttribute(TAG_GPS_LONGITUDE);
        String attribute4 = getAttribute(TAG_GPS_LONGITUDE_REF);
        if (!(attribute == null || attribute2 == null || attribute3 == null || attribute4 == null)) {
            try {
                return new double[]{a(attribute, attribute2), a(attribute3, attribute4)};
            } catch (IllegalArgumentException unused) {
                StringBuilder sb = new StringBuilder();
                sb.append("Latitude/longitude values are not parseable. ");
                sb.append(String.format("latValue=%s, latRef=%s, lngValue=%s, lngRef=%s", new Object[]{attribute, attribute2, attribute3, attribute4}));
                Log.w("ExifInterface", sb.toString());
            }
        }
        return null;
    }

    public void setGpsInfo(Location location) {
        if (location != null) {
            setAttribute(TAG_GPS_PROCESSING_METHOD, location.getProvider());
            setLatLong(location.getLatitude(), location.getLongitude());
            setAltitude(location.getAltitude());
            setAttribute(TAG_GPS_SPEED_REF, "K");
            setAttribute(TAG_GPS_SPEED, new Rational((double) ((location.getSpeed() * ((float) TimeUnit.HOURS.toSeconds(1))) / 1000.0f)).toString());
            String[] split = j.format(new Date(location.getTime())).split("\\s+");
            setAttribute(TAG_GPS_DATESTAMP, split[0]);
            setAttribute(TAG_GPS_TIMESTAMP, split[1]);
        }
    }

    public void setLatLong(double d2, double d3) {
        if (d2 < -90.0d || d2 > 90.0d || Double.isNaN(d2)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Latitude value ");
            sb.append(d2);
            sb.append(" is not valid.");
            throw new IllegalArgumentException(sb.toString());
        } else if (d3 < -180.0d || d3 > 180.0d || Double.isNaN(d3)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Longitude value ");
            sb2.append(d3);
            sb2.append(" is not valid.");
            throw new IllegalArgumentException(sb2.toString());
        } else {
            setAttribute(TAG_GPS_LATITUDE_REF, d2 >= 0.0d ? "N" : "S");
            setAttribute(TAG_GPS_LATITUDE, a(Math.abs(d2)));
            setAttribute(TAG_GPS_LONGITUDE_REF, d3 >= 0.0d ? LONGITUDE_EAST : LONGITUDE_WEST);
            setAttribute(TAG_GPS_LONGITUDE, a(Math.abs(d3)));
        }
    }

    public double getAltitude(double d2) {
        double attributeDouble = getAttributeDouble(TAG_GPS_ALTITUDE, -1.0d);
        int attributeInt = getAttributeInt(TAG_GPS_ALTITUDE_REF, -1);
        if (attributeDouble < 0.0d || attributeInt < 0) {
            return d2;
        }
        int i2 = 1;
        if (attributeInt == 1) {
            i2 = -1;
        }
        return attributeDouble * ((double) i2);
    }

    public void setAltitude(double d2) {
        String str = d2 >= 0.0d ? "0" : "1";
        setAttribute(TAG_GPS_ALTITUDE, new Rational(Math.abs(d2)).toString());
        setAttribute(TAG_GPS_ALTITUDE_REF, str);
    }

    @RestrictTo({Scope.LIBRARY})
    public void setDateTime(long j2) {
        long j3 = j2 % 1000;
        setAttribute(TAG_DATETIME, j.format(new Date(j2)));
        setAttribute(TAG_SUBSEC_TIME, Long.toString(j3));
    }

    @RestrictTo({Scope.LIBRARY})
    public long getDateTime() {
        long j2;
        String attribute = getAttribute(TAG_DATETIME);
        if (attribute == null || !T.matcher(attribute).matches()) {
            return -1;
        }
        try {
            Date parse = j.parse(attribute, new ParsePosition(0));
            if (parse == null) {
                return -1;
            }
            long time = parse.getTime();
            String attribute2 = getAttribute(TAG_SUBSEC_TIME);
            if (attribute2 != null) {
                try {
                    long parseLong = Long.parseLong(attribute2);
                    while (parseLong > 1000) {
                        parseLong /= 10;
                    }
                    j2 = time + parseLong;
                } catch (NumberFormatException unused) {
                }
                return j2;
            }
            j2 = time;
            return j2;
        } catch (IllegalArgumentException unused2) {
            return -1;
        }
    }

    @RestrictTo({Scope.LIBRARY})
    public long getGpsDateTime() {
        String attribute = getAttribute(TAG_GPS_DATESTAMP);
        String attribute2 = getAttribute(TAG_GPS_TIMESTAMP);
        if (attribute == null || attribute2 == null || (!T.matcher(attribute).matches() && !T.matcher(attribute2).matches())) {
            return -1;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(attribute);
        sb.append(TokenParser.SP);
        sb.append(attribute2);
        try {
            Date parse = j.parse(sb.toString(), new ParsePosition(0));
            if (parse == null) {
                return -1;
            }
            return parse.getTime();
        } catch (IllegalArgumentException unused) {
            return -1;
        }
    }

    private static double a(String str, String str2) {
        try {
            String[] split = str.split(",");
            String[] split2 = split[0].split("/");
            double parseDouble = Double.parseDouble(split2[0].trim()) / Double.parseDouble(split2[1].trim());
            String[] split3 = split[1].split("/");
            double parseDouble2 = Double.parseDouble(split3[0].trim()) / Double.parseDouble(split3[1].trim());
            String[] split4 = split[2].split("/");
            double parseDouble3 = parseDouble + (parseDouble2 / 60.0d) + ((Double.parseDouble(split4[0].trim()) / Double.parseDouble(split4[1].trim())) / 3600.0d);
            if (!str2.equals("S")) {
                if (!str2.equals(LONGITUDE_WEST)) {
                    if (!str2.equals("N")) {
                        if (!str2.equals(LONGITUDE_EAST)) {
                            throw new IllegalArgumentException();
                        }
                    }
                    return parseDouble3;
                }
            }
            return -parseDouble3;
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException unused) {
            throw new IllegalArgumentException();
        }
    }

    private String a(double d2) {
        long j2 = (long) d2;
        double d3 = d2 - ((double) j2);
        long j3 = (long) (d3 * 60.0d);
        long round = Math.round((d3 - (((double) j3) / 60.0d)) * 3600.0d * 1.0E7d);
        StringBuilder sb = new StringBuilder();
        sb.append(j2);
        sb.append("/1,");
        sb.append(j3);
        sb.append("/1,");
        sb.append(round);
        sb.append("/10000000");
        return sb.toString();
    }

    private int a(BufferedInputStream bufferedInputStream) {
        bufferedInputStream.mark(5000);
        byte[] bArr = new byte[5000];
        bufferedInputStream.read(bArr);
        bufferedInputStream.reset();
        if (a(bArr)) {
            return 4;
        }
        if (b(bArr)) {
            return 9;
        }
        if (c(bArr)) {
            return 7;
        }
        return d(bArr) ? 10 : 0;
    }

    private static boolean a(byte[] bArr) {
        for (int i2 = 0; i2 < a.length; i2++) {
            if (bArr[i2] != a[i2]) {
                return false;
            }
        }
        return true;
    }

    private boolean b(byte[] bArr) {
        byte[] bytes = "FUJIFILMCCD-RAW".getBytes(Charset.defaultCharset());
        for (int i2 = 0; i2 < bytes.length; i2++) {
            if (bArr[i2] != bytes[i2]) {
                return false;
            }
        }
        return true;
    }

    private boolean c(byte[] bArr) {
        ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream(bArr);
        this.H = e(byteOrderedDataInputStream);
        byteOrderedDataInputStream.a(this.H);
        short readShort = byteOrderedDataInputStream.readShort();
        byteOrderedDataInputStream.close();
        return readShort == 20306 || readShort == 21330;
    }

    private boolean d(byte[] bArr) {
        ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream(bArr);
        this.H = e(byteOrderedDataInputStream);
        byteOrderedDataInputStream.a(this.H);
        short readShort = byteOrderedDataInputStream.readShort();
        byteOrderedDataInputStream.close();
        return readShort == 85;
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x00be  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0164  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x015c A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x00b6 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(android.support.media.ExifInterface.ByteOrderedDataInputStream r9, int r10, int r11) {
        /*
            r8 = this;
            java.nio.ByteOrder r0 = java.nio.ByteOrder.BIG_ENDIAN
            r9.a(r0)
            long r0 = (long) r10
            r9.a(r0)
            byte r0 = r9.readByte()
            r1 = -1
            if (r0 == r1) goto L_0x002d
            java.io.IOException r9 = new java.io.IOException
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "Invalid marker: "
            r10.append(r11)
            r11 = r0 & 255(0xff, float:3.57E-43)
            java.lang.String r11 = java.lang.Integer.toHexString(r11)
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            r9.<init>(r10)
            throw r9
        L_0x002d:
            r2 = 1
            int r10 = r10 + r2
            byte r3 = r9.readByte()
            r4 = -40
            if (r3 == r4) goto L_0x0054
            java.io.IOException r9 = new java.io.IOException
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "Invalid marker: "
            r10.append(r11)
            r11 = r0 & 255(0xff, float:3.57E-43)
            java.lang.String r11 = java.lang.Integer.toHexString(r11)
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            r9.<init>(r10)
            throw r9
        L_0x0054:
            int r10 = r10 + r2
        L_0x0055:
            byte r0 = r9.readByte()
            if (r0 == r1) goto L_0x0078
            java.io.IOException r9 = new java.io.IOException
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "Invalid marker:"
            r10.append(r11)
            r11 = r0 & 255(0xff, float:3.57E-43)
            java.lang.String r11 = java.lang.Integer.toHexString(r11)
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            r9.<init>(r10)
            throw r9
        L_0x0078:
            int r10 = r10 + r2
            byte r0 = r9.readByte()
            int r10 = r10 + r2
            r3 = -39
            if (r0 == r3) goto L_0x0175
            r3 = -38
            if (r0 != r3) goto L_0x0088
            goto L_0x0175
        L_0x0088:
            int r3 = r9.readUnsignedShort()
            int r3 = r3 + -2
            int r10 = r10 + 2
            if (r3 >= 0) goto L_0x009a
            java.io.IOException r9 = new java.io.IOException
            java.lang.String r10 = "Invalid length"
            r9.<init>(r10)
            throw r9
        L_0x009a:
            r4 = -31
            r5 = 0
            if (r0 == r4) goto L_0x0118
            r4 = -2
            if (r0 == r4) goto L_0x00ea
            switch(r0) {
                case -64: goto L_0x00b0;
                case -63: goto L_0x00b0;
                case -62: goto L_0x00b0;
                case -61: goto L_0x00b0;
                default: goto L_0x00a5;
            }
        L_0x00a5:
            switch(r0) {
                case -59: goto L_0x00b0;
                case -58: goto L_0x00b0;
                case -57: goto L_0x00b0;
                default: goto L_0x00a8;
            }
        L_0x00a8:
            switch(r0) {
                case -55: goto L_0x00b0;
                case -54: goto L_0x00b0;
                case -53: goto L_0x00b0;
                default: goto L_0x00ab;
            }
        L_0x00ab:
            switch(r0) {
                case -51: goto L_0x00b0;
                case -50: goto L_0x00b0;
                case -49: goto L_0x00b0;
                default: goto L_0x00ae;
            }
        L_0x00ae:
            goto L_0x015a
        L_0x00b0:
            int r0 = r9.skipBytes(r2)
            if (r0 == r2) goto L_0x00be
            java.io.IOException r9 = new java.io.IOException
            java.lang.String r10 = "Invalid SOFx"
            r9.<init>(r10)
            throw r9
        L_0x00be:
            java.util.HashMap<java.lang.String, android.support.media.ExifInterface$ExifAttribute>[] r0 = r8.G
            r0 = r0[r11]
            java.lang.String r4 = "ImageLength"
            int r5 = r9.readUnsignedShort()
            long r5 = (long) r5
            java.nio.ByteOrder r7 = r8.H
            android.support.media.ExifInterface$ExifAttribute r5 = android.support.media.ExifInterface.ExifAttribute.a(r5, r7)
            r0.put(r4, r5)
            java.util.HashMap<java.lang.String, android.support.media.ExifInterface$ExifAttribute>[] r0 = r8.G
            r0 = r0[r11]
            java.lang.String r4 = "ImageWidth"
            int r5 = r9.readUnsignedShort()
            long r5 = (long) r5
            java.nio.ByteOrder r7 = r8.H
            android.support.media.ExifInterface$ExifAttribute r5 = android.support.media.ExifInterface.ExifAttribute.a(r5, r7)
            r0.put(r4, r5)
            int r3 = r3 + -5
            goto L_0x015a
        L_0x00ea:
            byte[] r0 = new byte[r3]
            int r4 = r9.read(r0)
            if (r4 == r3) goto L_0x00fa
            java.io.IOException r9 = new java.io.IOException
            java.lang.String r10 = "Invalid exif"
            r9.<init>(r10)
            throw r9
        L_0x00fa:
            java.lang.String r3 = "UserComment"
            java.lang.String r3 = r8.getAttribute(r3)
            if (r3 != 0) goto L_0x0116
            java.util.HashMap<java.lang.String, android.support.media.ExifInterface$ExifAttribute>[] r3 = r8.G
            r3 = r3[r2]
            java.lang.String r4 = "UserComment"
            java.lang.String r6 = new java.lang.String
            java.nio.charset.Charset r7 = C
            r6.<init>(r0, r7)
            android.support.media.ExifInterface$ExifAttribute r0 = android.support.media.ExifInterface.ExifAttribute.b(r6)
            r3.put(r4, r0)
        L_0x0116:
            r3 = 0
            goto L_0x015a
        L_0x0118:
            r0 = 6
            if (r3 >= r0) goto L_0x011c
            goto L_0x015a
        L_0x011c:
            byte[] r4 = new byte[r0]
            int r6 = r9.read(r4)
            if (r6 == r0) goto L_0x012c
            java.io.IOException r9 = new java.io.IOException
            java.lang.String r10 = "Invalid exif"
            r9.<init>(r10)
            throw r9
        L_0x012c:
            int r10 = r10 + 6
            int r3 = r3 + -6
            byte[] r0 = e
            boolean r0 = java.util.Arrays.equals(r4, r0)
            if (r0 != 0) goto L_0x0139
            goto L_0x015a
        L_0x0139:
            if (r3 > 0) goto L_0x0143
            java.io.IOException r9 = new java.io.IOException
            java.lang.String r10 = "Invalid exif"
            r9.<init>(r10)
            throw r9
        L_0x0143:
            r8.N = r10
            byte[] r0 = new byte[r3]
            int r4 = r9.read(r0)
            if (r4 == r3) goto L_0x0155
            java.io.IOException r9 = new java.io.IOException
            java.lang.String r10 = "Invalid exif"
            r9.<init>(r10)
            throw r9
        L_0x0155:
            int r10 = r10 + r3
            r8.a(r0, r11)
            goto L_0x0116
        L_0x015a:
            if (r3 >= 0) goto L_0x0164
            java.io.IOException r9 = new java.io.IOException
            java.lang.String r10 = "Invalid length"
            r9.<init>(r10)
            throw r9
        L_0x0164:
            int r0 = r9.skipBytes(r3)
            if (r0 == r3) goto L_0x0172
            java.io.IOException r9 = new java.io.IOException
            java.lang.String r10 = "Invalid JPEG segment"
            r9.<init>(r10)
            throw r9
        L_0x0172:
            int r10 = r10 + r3
            goto L_0x0055
        L_0x0175:
            java.nio.ByteOrder r10 = r8.H
            r9.a(r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.media.ExifInterface.a(android.support.media.ExifInterface$ByteOrderedDataInputStream, int, int):void");
    }

    private void a(ByteOrderedDataInputStream byteOrderedDataInputStream) {
        a(byteOrderedDataInputStream, byteOrderedDataInputStream.available());
        b(byteOrderedDataInputStream, 0);
        d(byteOrderedDataInputStream, 0);
        d(byteOrderedDataInputStream, 5);
        d(byteOrderedDataInputStream, 4);
        b((InputStream) byteOrderedDataInputStream);
        if (this.F == 8) {
            ExifAttribute exifAttribute = (ExifAttribute) this.G[1].get(TAG_MAKER_NOTE);
            if (exifAttribute != null) {
                ByteOrderedDataInputStream byteOrderedDataInputStream2 = new ByteOrderedDataInputStream(exifAttribute.c);
                byteOrderedDataInputStream2.a(this.H);
                byteOrderedDataInputStream2.a(6);
                b(byteOrderedDataInputStream2, 9);
                ExifAttribute exifAttribute2 = (ExifAttribute) this.G[9].get(TAG_COLOR_SPACE);
                if (exifAttribute2 != null) {
                    this.G[1].put(TAG_COLOR_SPACE, exifAttribute2);
                }
            }
        }
    }

    private void b(ByteOrderedDataInputStream byteOrderedDataInputStream) {
        byteOrderedDataInputStream.skipBytes(84);
        byte[] bArr = new byte[4];
        byte[] bArr2 = new byte[4];
        byteOrderedDataInputStream.read(bArr);
        byteOrderedDataInputStream.skipBytes(4);
        byteOrderedDataInputStream.read(bArr2);
        int i2 = ByteBuffer.wrap(bArr).getInt();
        int i3 = ByteBuffer.wrap(bArr2).getInt();
        a(byteOrderedDataInputStream, i2, 5);
        byteOrderedDataInputStream.a((long) i3);
        byteOrderedDataInputStream.a(ByteOrder.BIG_ENDIAN);
        int readInt = byteOrderedDataInputStream.readInt();
        for (int i4 = 0; i4 < readInt; i4++) {
            int readUnsignedShort = byteOrderedDataInputStream.readUnsignedShort();
            int readUnsignedShort2 = byteOrderedDataInputStream.readUnsignedShort();
            if (readUnsignedShort == q.a) {
                short readShort = byteOrderedDataInputStream.readShort();
                short readShort2 = byteOrderedDataInputStream.readShort();
                ExifAttribute a2 = ExifAttribute.a((int) readShort, this.H);
                ExifAttribute a3 = ExifAttribute.a((int) readShort2, this.H);
                this.G[0].put(TAG_IMAGE_LENGTH, a2);
                this.G[0].put(TAG_IMAGE_WIDTH, a3);
                return;
            }
            byteOrderedDataInputStream.skipBytes(readUnsignedShort2);
        }
    }

    private void c(ByteOrderedDataInputStream byteOrderedDataInputStream) {
        a(byteOrderedDataInputStream);
        ExifAttribute exifAttribute = (ExifAttribute) this.G[1].get(TAG_MAKER_NOTE);
        if (exifAttribute != null) {
            ByteOrderedDataInputStream byteOrderedDataInputStream2 = new ByteOrderedDataInputStream(exifAttribute.c);
            byteOrderedDataInputStream2.a(this.H);
            byte[] bArr = new byte[h.length];
            byteOrderedDataInputStream2.readFully(bArr);
            byteOrderedDataInputStream2.a(0);
            byte[] bArr2 = new byte[i.length];
            byteOrderedDataInputStream2.readFully(bArr2);
            if (Arrays.equals(bArr, h)) {
                byteOrderedDataInputStream2.a(8);
            } else if (Arrays.equals(bArr2, i)) {
                byteOrderedDataInputStream2.a(12);
            }
            b(byteOrderedDataInputStream2, 6);
            ExifAttribute exifAttribute2 = (ExifAttribute) this.G[7].get(TAG_ORF_PREVIEW_IMAGE_START);
            ExifAttribute exifAttribute3 = (ExifAttribute) this.G[7].get(TAG_ORF_PREVIEW_IMAGE_LENGTH);
            if (!(exifAttribute2 == null || exifAttribute3 == null)) {
                this.G[5].put(TAG_JPEG_INTERCHANGE_FORMAT, exifAttribute2);
                this.G[5].put(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, exifAttribute3);
            }
            ExifAttribute exifAttribute4 = (ExifAttribute) this.G[8].get(TAG_ORF_ASPECT_FRAME);
            if (exifAttribute4 != null) {
                int[] iArr = (int[]) exifAttribute4.d(this.H);
                if (iArr == null || iArr.length != 4) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Invalid aspect frame values. frame=");
                    sb.append(Arrays.toString(iArr));
                    Log.w("ExifInterface", sb.toString());
                } else if (iArr[2] > iArr[0] && iArr[3] > iArr[1]) {
                    int i2 = (iArr[2] - iArr[0]) + 1;
                    int i3 = (iArr[3] - iArr[1]) + 1;
                    if (i2 < i3) {
                        int i4 = i2 + i3;
                        i3 = i4 - i3;
                        i2 = i4 - i3;
                    }
                    ExifAttribute a2 = ExifAttribute.a(i2, this.H);
                    ExifAttribute a3 = ExifAttribute.a(i3, this.H);
                    this.G[0].put(TAG_IMAGE_WIDTH, a2);
                    this.G[0].put(TAG_IMAGE_LENGTH, a3);
                }
            }
        }
    }

    private void d(ByteOrderedDataInputStream byteOrderedDataInputStream) {
        a(byteOrderedDataInputStream);
        if (((ExifAttribute) this.G[0].get(TAG_RW2_JPG_FROM_RAW)) != null) {
            a(byteOrderedDataInputStream, this.R, 5);
        }
        ExifAttribute exifAttribute = (ExifAttribute) this.G[0].get(TAG_RW2_ISO);
        ExifAttribute exifAttribute2 = (ExifAttribute) this.G[1].get(TAG_PHOTOGRAPHIC_SENSITIVITY);
        if (exifAttribute != null && exifAttribute2 == null) {
            this.G[1].put(TAG_PHOTOGRAPHIC_SENSITIVITY, exifAttribute);
        }
    }

    private void a(InputStream inputStream, OutputStream outputStream) {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        ByteOrderedDataOutputStream byteOrderedDataOutputStream = new ByteOrderedDataOutputStream(outputStream, ByteOrder.BIG_ENDIAN);
        if (dataInputStream.readByte() != -1) {
            throw new IOException("Invalid marker");
        }
        byteOrderedDataOutputStream.a(-1);
        if (dataInputStream.readByte() != -40) {
            throw new IOException("Invalid marker");
        }
        byteOrderedDataOutputStream.a(-40);
        byteOrderedDataOutputStream.a(-1);
        byteOrderedDataOutputStream.a(-31);
        a(byteOrderedDataOutputStream, 6);
        byte[] bArr = new byte[4096];
        while (dataInputStream.readByte() == -1) {
            byte readByte = dataInputStream.readByte();
            if (readByte != -31) {
                switch (readByte) {
                    case -39:
                    case -38:
                        byteOrderedDataOutputStream.a(-1);
                        byteOrderedDataOutputStream.a((int) readByte);
                        b((InputStream) dataInputStream, (OutputStream) byteOrderedDataOutputStream);
                        return;
                    default:
                        byteOrderedDataOutputStream.a(-1);
                        byteOrderedDataOutputStream.a((int) readByte);
                        int readUnsignedShort = dataInputStream.readUnsignedShort();
                        byteOrderedDataOutputStream.c(readUnsignedShort);
                        int i2 = readUnsignedShort - 2;
                        if (i2 >= 0) {
                            while (i2 > 0) {
                                int read = dataInputStream.read(bArr, 0, Math.min(i2, bArr.length));
                                if (read < 0) {
                                    break;
                                } else {
                                    byteOrderedDataOutputStream.write(bArr, 0, read);
                                    i2 -= read;
                                }
                            }
                            break;
                        } else {
                            throw new IOException("Invalid length");
                        }
                }
            } else {
                int readUnsignedShort2 = dataInputStream.readUnsignedShort() - 2;
                if (readUnsignedShort2 < 0) {
                    throw new IOException("Invalid length");
                }
                byte[] bArr2 = new byte[6];
                if (readUnsignedShort2 >= 6) {
                    if (dataInputStream.read(bArr2) != 6) {
                        throw new IOException("Invalid exif");
                    } else if (Arrays.equals(bArr2, e)) {
                        int i3 = readUnsignedShort2 - 6;
                        if (dataInputStream.skipBytes(i3) != i3) {
                            throw new IOException("Invalid length");
                        }
                    }
                }
                byteOrderedDataOutputStream.a(-1);
                byteOrderedDataOutputStream.a((int) readByte);
                byteOrderedDataOutputStream.c(readUnsignedShort2 + 2);
                if (readUnsignedShort2 >= 6) {
                    readUnsignedShort2 -= 6;
                    byteOrderedDataOutputStream.write(bArr2);
                }
                while (readUnsignedShort2 > 0) {
                    int read2 = dataInputStream.read(bArr, 0, Math.min(readUnsignedShort2, bArr.length));
                    if (read2 >= 0) {
                        byteOrderedDataOutputStream.write(bArr, 0, read2);
                        readUnsignedShort2 -= read2;
                    }
                }
            }
        }
        throw new IOException("Invalid marker");
    }

    private void a(byte[] bArr, int i2) {
        ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream(bArr);
        a(byteOrderedDataInputStream, bArr.length);
        b(byteOrderedDataInputStream, i2);
    }

    private void c() {
        String attribute = getAttribute(TAG_DATETIME_ORIGINAL);
        if (attribute != null && getAttribute(TAG_DATETIME) == null) {
            this.G[0].put(TAG_DATETIME, ExifAttribute.b(attribute));
        }
        if (getAttribute(TAG_IMAGE_WIDTH) == null) {
            this.G[0].put(TAG_IMAGE_WIDTH, ExifAttribute.a(0, this.H));
        }
        if (getAttribute(TAG_IMAGE_LENGTH) == null) {
            this.G[0].put(TAG_IMAGE_LENGTH, ExifAttribute.a(0, this.H));
        }
        if (getAttribute(TAG_ORIENTATION) == null) {
            this.G[0].put(TAG_ORIENTATION, ExifAttribute.a(0, this.H));
        }
        if (getAttribute(TAG_LIGHT_SOURCE) == null) {
            this.G[1].put(TAG_LIGHT_SOURCE, ExifAttribute.a(0, this.H));
        }
    }

    private ByteOrder e(ByteOrderedDataInputStream byteOrderedDataInputStream) {
        short readShort = byteOrderedDataInputStream.readShort();
        if (readShort == 18761) {
            return ByteOrder.LITTLE_ENDIAN;
        }
        if (readShort == 19789) {
            return ByteOrder.BIG_ENDIAN;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Invalid byte order: ");
        sb.append(Integer.toHexString(readShort));
        throw new IOException(sb.toString());
    }

    private void a(ByteOrderedDataInputStream byteOrderedDataInputStream, int i2) {
        this.H = e(byteOrderedDataInputStream);
        byteOrderedDataInputStream.a(this.H);
        int readUnsignedShort = byteOrderedDataInputStream.readUnsignedShort();
        if (this.F == 7 || this.F == 10 || readUnsignedShort == 42) {
            int readInt = byteOrderedDataInputStream.readInt();
            if (readInt < 8 || readInt >= i2) {
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid first Ifd offset: ");
                sb.append(readInt);
                throw new IOException(sb.toString());
            }
            int i3 = readInt - 8;
            if (i3 > 0 && byteOrderedDataInputStream.skipBytes(i3) != i3) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Couldn't jump to first Ifd: ");
                sb2.append(i3);
                throw new IOException(sb2.toString());
            }
            return;
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Invalid start code: ");
        sb3.append(Integer.toHexString(readUnsignedShort));
        throw new IOException(sb3.toString());
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x00e9  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00f1  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x01b1  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x01ff  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(android.support.media.ExifInterface.ByteOrderedDataInputStream r23, int r24) {
        /*
            r22 = this;
            r0 = r22
            r1 = r23
            r2 = r24
            int r3 = r23.f
            int r3 = r3 + 2
            int r4 = r23.e
            if (r3 <= r4) goto L_0x0013
            return
        L_0x0013:
            short r3 = r23.readShort()
            int r4 = r23.f
            int r5 = r3 * 12
            int r4 = r4 + r5
            int r5 = r23.e
            if (r4 <= r5) goto L_0x0025
            return
        L_0x0025:
            r5 = 0
        L_0x0026:
            if (r5 >= r3) goto L_0x0276
            int r8 = r23.readUnsignedShort()
            int r9 = r23.readUnsignedShort()
            int r10 = r23.readInt()
            int r11 = r23.a()
            long r11 = (long) r11
            r13 = 4
            r16 = r5
            long r4 = r11 + r13
            java.util.HashMap<java.lang.Integer, android.support.media.ExifInterface$ExifTag>[] r11 = y
            r11 = r11[r2]
            java.lang.Integer r12 = java.lang.Integer.valueOf(r8)
            java.lang.Object r11 = r11.get(r12)
            android.support.media.ExifInterface$ExifTag r11 = (android.support.media.ExifInterface.ExifTag) r11
            r12 = 7
            r17 = 0
            if (r11 != 0) goto L_0x006a
            java.lang.String r15 = "ExifInterface"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Skip the tag entry since tag number is not defined: "
            r6.append(r7)
            r6.append(r8)
            java.lang.String r6 = r6.toString()
            android.util.Log.w(r15, r6)
            goto L_0x00e4
        L_0x006a:
            if (r9 <= 0) goto L_0x00ce
            int[] r6 = c
            int r6 = r6.length
            if (r9 < r6) goto L_0x0072
            goto L_0x00ce
        L_0x0072:
            boolean r6 = r11.a(r9)
            if (r6 != 0) goto L_0x009d
            java.lang.String r6 = "ExifInterface"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r15 = "Skip the tag entry since data format ("
            r7.append(r15)
            java.lang.String[] r15 = b
            r15 = r15[r9]
            r7.append(r15)
            java.lang.String r15 = ") is unexpected for tag: "
            r7.append(r15)
            java.lang.String r15 = r11.b
            r7.append(r15)
            java.lang.String r7 = r7.toString()
            android.util.Log.w(r6, r7)
            goto L_0x00e4
        L_0x009d:
            if (r9 != r12) goto L_0x00a1
            int r9 = r11.c
        L_0x00a1:
            long r6 = (long) r10
            int[] r15 = c
            r15 = r15[r9]
            long r12 = (long) r15
            long r6 = r6 * r12
            int r12 = (r6 > r17 ? 1 : (r6 == r17 ? 0 : -1))
            if (r12 < 0) goto L_0x00b7
            r12 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r14 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1))
            if (r14 <= 0) goto L_0x00b5
            goto L_0x00b7
        L_0x00b5:
            r12 = 1
            goto L_0x00e7
        L_0x00b7:
            java.lang.String r12 = "ExifInterface"
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r14 = "Skip the tag entry since the number of components is invalid: "
            r13.append(r14)
            r13.append(r10)
            java.lang.String r13 = r13.toString()
            android.util.Log.w(r12, r13)
            goto L_0x00e6
        L_0x00ce:
            java.lang.String r6 = "ExifInterface"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r12 = "Skip the tag entry since data format is invalid: "
            r7.append(r12)
            r7.append(r9)
            java.lang.String r7 = r7.toString()
            android.util.Log.w(r6, r7)
        L_0x00e4:
            r6 = r17
        L_0x00e6:
            r12 = 0
        L_0x00e7:
            if (r12 != 0) goto L_0x00f1
            r1.a(r4)
            r6 = r2
            r19 = r3
            goto L_0x026e
        L_0x00f1:
            r12 = 4
            int r14 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1))
            if (r14 <= 0) goto L_0x019d
            int r12 = r23.readInt()
            int r13 = r0.F
            r14 = 7
            if (r13 != r14) goto L_0x0158
            java.lang.String r13 = "MakerNote"
            java.lang.String r14 = r11.b
            boolean r13 = r13.equals(r14)
            if (r13 == 0) goto L_0x010f
            r0.O = r12
        L_0x010c:
            r19 = r3
            goto L_0x016c
        L_0x010f:
            r13 = 6
            if (r2 != r13) goto L_0x010c
            java.lang.String r14 = "ThumbnailImage"
            java.lang.String r15 = r11.b
            boolean r14 = r14.equals(r15)
            if (r14 == 0) goto L_0x010c
            r0.P = r12
            r0.Q = r10
            java.nio.ByteOrder r14 = r0.H
            android.support.media.ExifInterface$ExifAttribute r13 = android.support.media.ExifInterface.ExifAttribute.a(r13, r14)
            int r14 = r0.P
            long r14 = (long) r14
            r19 = r3
            java.nio.ByteOrder r3 = r0.H
            android.support.media.ExifInterface$ExifAttribute r3 = android.support.media.ExifInterface.ExifAttribute.a(r14, r3)
            int r14 = r0.Q
            long r14 = (long) r14
            java.nio.ByteOrder r2 = r0.H
            android.support.media.ExifInterface$ExifAttribute r2 = android.support.media.ExifInterface.ExifAttribute.a(r14, r2)
            java.util.HashMap<java.lang.String, android.support.media.ExifInterface$ExifAttribute>[] r14 = r0.G
            r15 = 4
            r14 = r14[r15]
            java.lang.String r15 = "Compression"
            r14.put(r15, r13)
            java.util.HashMap<java.lang.String, android.support.media.ExifInterface$ExifAttribute>[] r13 = r0.G
            r14 = 4
            r13 = r13[r14]
            java.lang.String r15 = "JPEGInterchangeFormat"
            r13.put(r15, r3)
            java.util.HashMap<java.lang.String, android.support.media.ExifInterface$ExifAttribute>[] r3 = r0.G
            r3 = r3[r14]
            java.lang.String r13 = "JPEGInterchangeFormatLength"
            r3.put(r13, r2)
            goto L_0x016c
        L_0x0158:
            r19 = r3
            int r2 = r0.F
            r3 = 10
            if (r2 != r3) goto L_0x016c
            java.lang.String r2 = "JpgFromRaw"
            java.lang.String r3 = r11.b
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x016c
            r0.R = r12
        L_0x016c:
            long r2 = (long) r12
            long r13 = r2 + r6
            int r15 = r23.e
            r20 = r10
            r21 = r11
            long r10 = (long) r15
            int r15 = (r13 > r10 ? 1 : (r13 == r10 ? 0 : -1))
            if (r15 > 0) goto L_0x0180
            r1.a(r2)
            goto L_0x01a3
        L_0x0180:
            java.lang.String r2 = "ExifInterface"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r6 = "Skip the tag entry since data offset is invalid: "
            r3.append(r6)
            r3.append(r12)
            java.lang.String r3 = r3.toString()
            android.util.Log.w(r2, r3)
            r1.a(r4)
        L_0x0199:
            r6 = r24
            goto L_0x026e
        L_0x019d:
            r19 = r3
            r20 = r10
            r21 = r11
        L_0x01a3:
            java.util.HashMap<java.lang.Integer, java.lang.Integer> r2 = B
            java.lang.Integer r3 = java.lang.Integer.valueOf(r8)
            java.lang.Object r2 = r2.get(r3)
            java.lang.Integer r2 = (java.lang.Integer) r2
            if (r2 == 0) goto L_0x01ff
            r6 = -1
            switch(r9) {
                case 3: goto L_0x01c8;
                case 4: goto L_0x01c3;
                case 8: goto L_0x01bd;
                case 9: goto L_0x01b7;
                case 13: goto L_0x01b7;
                default: goto L_0x01b6;
            }
        L_0x01b6:
            goto L_0x01cd
        L_0x01b7:
            int r3 = r23.readInt()
            long r6 = (long) r3
            goto L_0x01cd
        L_0x01bd:
            short r3 = r23.readShort()
            long r6 = (long) r3
            goto L_0x01cd
        L_0x01c3:
            long r6 = r23.b()
            goto L_0x01cd
        L_0x01c8:
            int r3 = r23.readUnsignedShort()
            long r6 = (long) r3
        L_0x01cd:
            int r3 = (r6 > r17 ? 1 : (r6 == r17 ? 0 : -1))
            if (r3 <= 0) goto L_0x01e5
            int r3 = r23.e
            long r8 = (long) r3
            int r3 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r3 >= 0) goto L_0x01e5
            r1.a(r6)
            int r2 = r2.intValue()
            r0.b(r1, r2)
            goto L_0x01fb
        L_0x01e5:
            java.lang.String r2 = "ExifInterface"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r8 = "Skip jump into the IFD since its offset is invalid: "
            r3.append(r8)
            r3.append(r6)
            java.lang.String r3 = r3.toString()
            android.util.Log.w(r2, r3)
        L_0x01fb:
            r1.a(r4)
            goto L_0x0199
        L_0x01ff:
            int r2 = (int) r6
            byte[] r2 = new byte[r2]
            r1.readFully(r2)
            android.support.media.ExifInterface$ExifAttribute r3 = new android.support.media.ExifInterface$ExifAttribute
            r6 = 0
            r7 = r20
            r3.<init>(r9, r7, r2)
            java.util.HashMap<java.lang.String, android.support.media.ExifInterface$ExifAttribute>[] r2 = r0.G
            r6 = r24
            r2 = r2[r6]
            r11 = r21
            java.lang.String r7 = r11.b
            r2.put(r7, r3)
            java.lang.String r2 = "DNGVersion"
            java.lang.String r7 = r11.b
            boolean r2 = r2.equals(r7)
            if (r2 == 0) goto L_0x0227
            r2 = 3
            r0.F = r2
        L_0x0227:
            java.lang.String r2 = "Make"
            java.lang.String r7 = r11.b
            boolean r2 = r2.equals(r7)
            if (r2 != 0) goto L_0x023b
            java.lang.String r2 = "Model"
            java.lang.String r7 = r11.b
            boolean r2 = r2.equals(r7)
            if (r2 == 0) goto L_0x0249
        L_0x023b:
            java.nio.ByteOrder r2 = r0.H
            java.lang.String r2 = r3.c(r2)
            java.lang.String r7 = "PENTAX"
            boolean r2 = r2.contains(r7)
            if (r2 != 0) goto L_0x025e
        L_0x0249:
            java.lang.String r2 = "Compression"
            java.lang.String r7 = r11.b
            boolean r2 = r2.equals(r7)
            if (r2 == 0) goto L_0x0262
            java.nio.ByteOrder r2 = r0.H
            int r2 = r3.b(r2)
            r3 = 65535(0xffff, float:9.1834E-41)
            if (r2 != r3) goto L_0x0262
        L_0x025e:
            r2 = 8
            r0.F = r2
        L_0x0262:
            int r2 = r23.a()
            long r2 = (long) r2
            int r7 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r7 == 0) goto L_0x026e
            r1.a(r4)
        L_0x026e:
            int r5 = r16 + 1
            short r5 = (short) r5
            r2 = r6
            r3 = r19
            goto L_0x0026
        L_0x0276:
            int r2 = r23.a()
            r3 = 4
            int r2 = r2 + r3
            int r4 = r23.e
            if (r2 > r4) goto L_0x02b0
            int r2 = r23.readInt()
            r4 = 8
            if (r2 <= r4) goto L_0x02b0
            int r4 = r23.e
            if (r2 >= r4) goto L_0x02b0
            long r4 = (long) r2
            r1.a(r4)
            java.util.HashMap<java.lang.String, android.support.media.ExifInterface$ExifAttribute>[] r2 = r0.G
            r2 = r2[r3]
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L_0x02a2
            r0.b(r1, r3)
            goto L_0x02b0
        L_0x02a2:
            java.util.HashMap<java.lang.String, android.support.media.ExifInterface$ExifAttribute>[] r2 = r0.G
            r3 = 5
            r2 = r2[r3]
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L_0x02b0
            r0.b(r1, r3)
        L_0x02b0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.media.ExifInterface.b(android.support.media.ExifInterface$ByteOrderedDataInputStream, int):void");
    }

    private void c(ByteOrderedDataInputStream byteOrderedDataInputStream, int i2) {
        ExifAttribute exifAttribute = (ExifAttribute) this.G[i2].get(TAG_IMAGE_WIDTH);
        if (((ExifAttribute) this.G[i2].get(TAG_IMAGE_LENGTH)) == null || exifAttribute == null) {
            ExifAttribute exifAttribute2 = (ExifAttribute) this.G[i2].get(TAG_JPEG_INTERCHANGE_FORMAT);
            if (exifAttribute2 != null) {
                a(byteOrderedDataInputStream, exifAttribute2.b(this.H), i2);
            }
        }
    }

    private void f(ByteOrderedDataInputStream byteOrderedDataInputStream) {
        HashMap<String, ExifAttribute> hashMap = this.G[4];
        ExifAttribute exifAttribute = (ExifAttribute) hashMap.get(TAG_COMPRESSION);
        if (exifAttribute != null) {
            this.M = exifAttribute.b(this.H);
            int i2 = this.M;
            if (i2 != 1) {
                switch (i2) {
                    case 6:
                        a(byteOrderedDataInputStream, (HashMap) hashMap);
                        return;
                    case 7:
                        break;
                    default:
                        return;
                }
            }
            if (a((HashMap) hashMap)) {
                b(byteOrderedDataInputStream, (HashMap) hashMap);
                return;
            }
            return;
        }
        this.M = 6;
        a(byteOrderedDataInputStream, (HashMap) hashMap);
    }

    private void a(ByteOrderedDataInputStream byteOrderedDataInputStream, HashMap hashMap) {
        ExifAttribute exifAttribute = (ExifAttribute) hashMap.get(TAG_JPEG_INTERCHANGE_FORMAT);
        ExifAttribute exifAttribute2 = (ExifAttribute) hashMap.get(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
        if (exifAttribute != null && exifAttribute2 != null) {
            int b2 = exifAttribute.b(this.H);
            int min = Math.min(exifAttribute2.b(this.H), byteOrderedDataInputStream.available() - b2);
            if (this.F == 4 || this.F == 9 || this.F == 10) {
                b2 += this.N;
            } else if (this.F == 7) {
                b2 += this.O;
            }
            if (b2 > 0 && min > 0) {
                this.I = true;
                this.J = b2;
                this.K = min;
                if (this.D == null && this.E == null) {
                    byte[] bArr = new byte[min];
                    byteOrderedDataInputStream.a((long) b2);
                    byteOrderedDataInputStream.readFully(bArr);
                    this.L = bArr;
                }
            }
        }
    }

    private void b(ByteOrderedDataInputStream byteOrderedDataInputStream, HashMap hashMap) {
        ExifAttribute exifAttribute = (ExifAttribute) hashMap.get(TAG_STRIP_OFFSETS);
        ExifAttribute exifAttribute2 = (ExifAttribute) hashMap.get(TAG_STRIP_BYTE_COUNTS);
        if (!(exifAttribute == null || exifAttribute2 == null)) {
            long[] a2 = a(exifAttribute.d(this.H));
            long[] a3 = a(exifAttribute2.d(this.H));
            if (a2 == null) {
                Log.w("ExifInterface", "stripOffsets should not be null.");
            } else if (a3 == null) {
                Log.w("ExifInterface", "stripByteCounts should not be null.");
            } else {
                long j2 = 0;
                int i2 = 0;
                while (i2 < a3.length) {
                    i2++;
                    j2 += a3[i2];
                }
                byte[] bArr = new byte[((int) j2)];
                int i3 = 0;
                int i4 = 0;
                for (int i5 = 0; i5 < a2.length; i5++) {
                    int i6 = (int) a3[i5];
                    int i7 = ((int) a2[i5]) - i3;
                    if (i7 < 0) {
                        Log.d("ExifInterface", "Invalid strip offset value");
                    }
                    byteOrderedDataInputStream.a((long) i7);
                    int i8 = i3 + i7;
                    byte[] bArr2 = new byte[i6];
                    byteOrderedDataInputStream.read(bArr2);
                    i3 = i8 + i6;
                    System.arraycopy(bArr2, 0, bArr, i4, bArr2.length);
                    i4 += bArr2.length;
                }
                this.I = true;
                this.L = bArr;
                this.K = bArr.length;
            }
        }
    }

    private boolean a(HashMap hashMap) {
        ExifAttribute exifAttribute = (ExifAttribute) hashMap.get(TAG_BITS_PER_SAMPLE);
        if (exifAttribute != null) {
            int[] iArr = (int[]) exifAttribute.d(this.H);
            if (Arrays.equals(BITS_PER_SAMPLE_RGB, iArr)) {
                return true;
            }
            if (this.F == 3) {
                ExifAttribute exifAttribute2 = (ExifAttribute) hashMap.get(TAG_PHOTOMETRIC_INTERPRETATION);
                if (exifAttribute2 != null) {
                    int b2 = exifAttribute2.b(this.H);
                    if ((b2 == 1 && Arrays.equals(iArr, BITS_PER_SAMPLE_GREYSCALE_2)) || (b2 == 6 && Arrays.equals(iArr, BITS_PER_SAMPLE_RGB))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean b(HashMap hashMap) {
        ExifAttribute exifAttribute = (ExifAttribute) hashMap.get(TAG_IMAGE_LENGTH);
        ExifAttribute exifAttribute2 = (ExifAttribute) hashMap.get(TAG_IMAGE_WIDTH);
        if (!(exifAttribute == null || exifAttribute2 == null)) {
            int b2 = exifAttribute.b(this.H);
            int b3 = exifAttribute2.b(this.H);
            if (b2 <= 512 && b3 <= 512) {
                return true;
            }
        }
        return false;
    }

    private void b(InputStream inputStream) {
        a(0, 5);
        a(0, 4);
        a(5, 4);
        ExifAttribute exifAttribute = (ExifAttribute) this.G[1].get(TAG_PIXEL_X_DIMENSION);
        ExifAttribute exifAttribute2 = (ExifAttribute) this.G[1].get(TAG_PIXEL_Y_DIMENSION);
        if (!(exifAttribute == null || exifAttribute2 == null)) {
            this.G[0].put(TAG_IMAGE_WIDTH, exifAttribute);
            this.G[0].put(TAG_IMAGE_LENGTH, exifAttribute2);
        }
        if (this.G[4].isEmpty() && b((HashMap) this.G[5])) {
            this.G[4] = this.G[5];
            this.G[5] = new HashMap<>();
        }
        if (!b((HashMap) this.G[4])) {
            Log.d("ExifInterface", "No image meets the size requirements of a thumbnail image.");
        }
    }

    private void d(ByteOrderedDataInputStream byteOrderedDataInputStream, int i2) {
        ExifAttribute exifAttribute;
        ExifAttribute exifAttribute2;
        ExifAttribute exifAttribute3 = (ExifAttribute) this.G[i2].get(TAG_DEFAULT_CROP_SIZE);
        ExifAttribute exifAttribute4 = (ExifAttribute) this.G[i2].get(TAG_RW2_SENSOR_TOP_BORDER);
        ExifAttribute exifAttribute5 = (ExifAttribute) this.G[i2].get(TAG_RW2_SENSOR_LEFT_BORDER);
        ExifAttribute exifAttribute6 = (ExifAttribute) this.G[i2].get(TAG_RW2_SENSOR_BOTTOM_BORDER);
        ExifAttribute exifAttribute7 = (ExifAttribute) this.G[i2].get(TAG_RW2_SENSOR_RIGHT_BORDER);
        if (exifAttribute3 != null) {
            if (exifAttribute3.a == 5) {
                Rational[] rationalArr = (Rational[]) exifAttribute3.d(this.H);
                if (rationalArr == null || rationalArr.length != 2) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Invalid crop size values. cropSize=");
                    sb.append(Arrays.toString(rationalArr));
                    Log.w("ExifInterface", sb.toString());
                    return;
                }
                exifAttribute2 = ExifAttribute.a(rationalArr[0], this.H);
                exifAttribute = ExifAttribute.a(rationalArr[1], this.H);
            } else {
                int[] iArr = (int[]) exifAttribute3.d(this.H);
                if (iArr == null || iArr.length != 2) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Invalid crop size values. cropSize=");
                    sb2.append(Arrays.toString(iArr));
                    Log.w("ExifInterface", sb2.toString());
                    return;
                }
                exifAttribute2 = ExifAttribute.a(iArr[0], this.H);
                exifAttribute = ExifAttribute.a(iArr[1], this.H);
            }
            this.G[i2].put(TAG_IMAGE_WIDTH, exifAttribute2);
            this.G[i2].put(TAG_IMAGE_LENGTH, exifAttribute);
        } else if (exifAttribute4 == null || exifAttribute5 == null || exifAttribute6 == null || exifAttribute7 == null) {
            c(byteOrderedDataInputStream, i2);
        } else {
            int b2 = exifAttribute4.b(this.H);
            int b3 = exifAttribute6.b(this.H);
            int b4 = exifAttribute7.b(this.H);
            int b5 = exifAttribute5.b(this.H);
            if (b3 > b2 && b4 > b5) {
                int i3 = b4 - b5;
                ExifAttribute a2 = ExifAttribute.a(b3 - b2, this.H);
                ExifAttribute a3 = ExifAttribute.a(i3, this.H);
                this.G[i2].put(TAG_IMAGE_LENGTH, a2);
                this.G[i2].put(TAG_IMAGE_WIDTH, a3);
            }
        }
    }

    private int a(ByteOrderedDataOutputStream byteOrderedDataOutputStream, int i2) {
        ByteOrderedDataOutputStream byteOrderedDataOutputStream2 = byteOrderedDataOutputStream;
        int[] iArr = new int[d.length];
        int[] iArr2 = new int[d.length];
        for (ExifTag exifTag : v) {
            b(exifTag.b);
        }
        b(w.b);
        b(x.b);
        for (int i3 = 0; i3 < d.length; i3++) {
            for (Object obj : this.G[i3].entrySet().toArray()) {
                Entry entry = (Entry) obj;
                if (entry.getValue() == null) {
                    this.G[i3].remove(entry.getKey());
                }
            }
        }
        if (!this.G[1].isEmpty()) {
            this.G[0].put(v[1].b, ExifAttribute.a(0, this.H));
        }
        if (!this.G[2].isEmpty()) {
            this.G[0].put(v[2].b, ExifAttribute.a(0, this.H));
        }
        if (!this.G[3].isEmpty()) {
            this.G[1].put(v[3].b, ExifAttribute.a(0, this.H));
        }
        if (this.I) {
            this.G[4].put(w.b, ExifAttribute.a(0, this.H));
            this.G[4].put(x.b, ExifAttribute.a((long) this.K, this.H));
        }
        for (int i4 = 0; i4 < d.length; i4++) {
            int i5 = 0;
            for (Entry value : this.G[i4].entrySet()) {
                int a2 = ((ExifAttribute) value.getValue()).a();
                if (a2 > 4) {
                    i5 += a2;
                }
            }
            iArr2[i4] = iArr2[i4] + i5;
        }
        int i6 = 8;
        for (int i7 = 0; i7 < d.length; i7++) {
            if (!this.G[i7].isEmpty()) {
                iArr[i7] = i6;
                i6 += (this.G[i7].size() * 12) + 2 + 4 + iArr2[i7];
            }
        }
        if (this.I) {
            this.G[4].put(w.b, ExifAttribute.a((long) i6, this.H));
            this.J = i2 + i6;
            i6 += this.K;
        }
        int i8 = i6 + 8;
        if (!this.G[1].isEmpty()) {
            this.G[0].put(v[1].b, ExifAttribute.a((long) iArr[1], this.H));
        }
        if (!this.G[2].isEmpty()) {
            this.G[0].put(v[2].b, ExifAttribute.a((long) iArr[2], this.H));
        }
        if (!this.G[3].isEmpty()) {
            this.G[1].put(v[3].b, ExifAttribute.a((long) iArr[3], this.H));
        }
        byteOrderedDataOutputStream2.c(i8);
        byteOrderedDataOutputStream2.write(e);
        byteOrderedDataOutputStream2.a(this.H == ByteOrder.BIG_ENDIAN ? (short) 19789 : 18761);
        byteOrderedDataOutputStream2.a(this.H);
        byteOrderedDataOutputStream2.c(42);
        byteOrderedDataOutputStream2.a(8);
        for (int i9 = 0; i9 < d.length; i9++) {
            if (!this.G[i9].isEmpty()) {
                byteOrderedDataOutputStream2.c(this.G[i9].size());
                int size = iArr[i9] + 2 + (this.G[i9].size() * 12) + 4;
                for (Entry entry2 : this.G[i9].entrySet()) {
                    int i10 = ((ExifTag) z[i9].get(entry2.getKey())).a;
                    ExifAttribute exifAttribute = (ExifAttribute) entry2.getValue();
                    int a3 = exifAttribute.a();
                    byteOrderedDataOutputStream2.c(i10);
                    byteOrderedDataOutputStream2.c(exifAttribute.a);
                    byteOrderedDataOutputStream2.b(exifAttribute.b);
                    if (a3 > 4) {
                        byteOrderedDataOutputStream2.a((long) size);
                        size += a3;
                    } else {
                        byteOrderedDataOutputStream2.write(exifAttribute.c);
                        if (a3 < 4) {
                            while (a3 < 4) {
                                byteOrderedDataOutputStream2.a(0);
                                a3++;
                            }
                        }
                    }
                }
                if (i9 != 0 || this.G[4].isEmpty()) {
                    byteOrderedDataOutputStream2.a(0);
                } else {
                    byteOrderedDataOutputStream2.a((long) iArr[4]);
                }
                for (Entry value2 : this.G[i9].entrySet()) {
                    ExifAttribute exifAttribute2 = (ExifAttribute) value2.getValue();
                    if (exifAttribute2.c.length > 4) {
                        byteOrderedDataOutputStream2.write(exifAttribute2.c, 0, exifAttribute2.c.length);
                    }
                }
            }
        }
        if (this.I) {
            byteOrderedDataOutputStream2.write(getThumbnailBytes());
        }
        byteOrderedDataOutputStream2.a(ByteOrder.BIG_ENDIAN);
        return i8;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:69:?, code lost:
        java.lang.Double.parseDouble(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0185, code lost:
        return new android.util.Pair<>(java.lang.Integer.valueOf(12), java.lang.Integer.valueOf(-1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0193, code lost:
        return new android.util.Pair<>(java.lang.Integer.valueOf(2), java.lang.Integer.valueOf(-1));
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:68:0x0173 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.util.Pair<java.lang.Integer, java.lang.Integer> c(java.lang.String r11) {
        /*
            java.lang.String r0 = ","
            boolean r0 = r11.contains(r0)
            r1 = 0
            r2 = 1
            r3 = 2
            r4 = -1
            if (r0 == 0) goto L_0x00b1
            java.lang.String r0 = ","
            java.lang.String[] r11 = r11.split(r0)
            r0 = r11[r1]
            android.util.Pair r0 = c(r0)
            java.lang.Object r1 = r0.first
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            if (r1 != r3) goto L_0x0023
            return r0
        L_0x0023:
            int r1 = r11.length
            if (r2 >= r1) goto L_0x00b0
            r1 = r11[r2]
            android.util.Pair r1 = c(r1)
            java.lang.Object r5 = r1.first
            java.lang.Integer r5 = (java.lang.Integer) r5
            java.lang.Object r6 = r0.first
            boolean r5 = r5.equals(r6)
            if (r5 != 0) goto L_0x0047
            java.lang.Object r5 = r1.second
            java.lang.Integer r5 = (java.lang.Integer) r5
            java.lang.Object r6 = r0.first
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0045
            goto L_0x0047
        L_0x0045:
            r5 = -1
            goto L_0x004f
        L_0x0047:
            java.lang.Object r5 = r0.first
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
        L_0x004f:
            java.lang.Object r6 = r0.second
            java.lang.Integer r6 = (java.lang.Integer) r6
            int r6 = r6.intValue()
            if (r6 == r4) goto L_0x007a
            java.lang.Object r6 = r1.first
            java.lang.Integer r6 = (java.lang.Integer) r6
            java.lang.Object r7 = r0.second
            boolean r6 = r6.equals(r7)
            if (r6 != 0) goto L_0x0071
            java.lang.Object r1 = r1.second
            java.lang.Integer r1 = (java.lang.Integer) r1
            java.lang.Object r6 = r0.second
            boolean r1 = r1.equals(r6)
            if (r1 == 0) goto L_0x007a
        L_0x0071:
            java.lang.Object r1 = r0.second
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            goto L_0x007b
        L_0x007a:
            r1 = -1
        L_0x007b:
            if (r5 != r4) goto L_0x008d
            if (r1 != r4) goto L_0x008d
            android.util.Pair r11 = new android.util.Pair
            java.lang.Integer r0 = java.lang.Integer.valueOf(r3)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)
            r11.<init>(r0, r1)
            return r11
        L_0x008d:
            if (r5 != r4) goto L_0x009d
            android.util.Pair r0 = new android.util.Pair
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r4)
            r0.<init>(r1, r5)
            goto L_0x00ac
        L_0x009d:
            if (r1 != r4) goto L_0x00ac
            android.util.Pair r0 = new android.util.Pair
            java.lang.Integer r1 = java.lang.Integer.valueOf(r5)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r4)
            r0.<init>(r1, r5)
        L_0x00ac:
            int r2 = r2 + 1
            goto L_0x0023
        L_0x00b0:
            return r0
        L_0x00b1:
            java.lang.String r0 = "/"
            boolean r0 = r11.contains(r0)
            r5 = 0
            if (r0 == 0) goto L_0x0122
            java.lang.String r0 = "/"
            java.lang.String[] r11 = r11.split(r0)
            int r0 = r11.length
            if (r0 != r3) goto L_0x0114
            r0 = r11[r1]     // Catch:{ NumberFormatException -> 0x0114 }
            double r0 = java.lang.Double.parseDouble(r0)     // Catch:{ NumberFormatException -> 0x0114 }
            long r0 = (long) r0     // Catch:{ NumberFormatException -> 0x0114 }
            r11 = r11[r2]     // Catch:{ NumberFormatException -> 0x0114 }
            double r7 = java.lang.Double.parseDouble(r11)     // Catch:{ NumberFormatException -> 0x0114 }
            long r7 = (long) r7     // Catch:{ NumberFormatException -> 0x0114 }
            int r11 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            r2 = 10
            if (r11 < 0) goto L_0x0106
            int r11 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r11 >= 0) goto L_0x00dd
            goto L_0x0106
        L_0x00dd:
            r5 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r11 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            r0 = 5
            if (r11 > 0) goto L_0x00f8
            int r11 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r11 <= 0) goto L_0x00ea
            goto L_0x00f8
        L_0x00ea:
            android.util.Pair r11 = new android.util.Pair     // Catch:{ NumberFormatException -> 0x0114 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r2)     // Catch:{ NumberFormatException -> 0x0114 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ NumberFormatException -> 0x0114 }
            r11.<init>(r1, r0)     // Catch:{ NumberFormatException -> 0x0114 }
            return r11
        L_0x00f8:
            android.util.Pair r11 = new android.util.Pair     // Catch:{ NumberFormatException -> 0x0114 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ NumberFormatException -> 0x0114 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)     // Catch:{ NumberFormatException -> 0x0114 }
            r11.<init>(r0, r1)     // Catch:{ NumberFormatException -> 0x0114 }
            return r11
        L_0x0106:
            android.util.Pair r11 = new android.util.Pair     // Catch:{ NumberFormatException -> 0x0114 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r2)     // Catch:{ NumberFormatException -> 0x0114 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)     // Catch:{ NumberFormatException -> 0x0114 }
            r11.<init>(r0, r1)     // Catch:{ NumberFormatException -> 0x0114 }
            return r11
        L_0x0114:
            android.util.Pair r11 = new android.util.Pair
            java.lang.Integer r0 = java.lang.Integer.valueOf(r3)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)
            r11.<init>(r0, r1)
            return r11
        L_0x0122:
            long r0 = java.lang.Long.parseLong(r11)     // Catch:{ NumberFormatException -> 0x0173 }
            java.lang.Long r0 = java.lang.Long.valueOf(r0)     // Catch:{ NumberFormatException -> 0x0173 }
            long r1 = r0.longValue()     // Catch:{ NumberFormatException -> 0x0173 }
            int r7 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            r1 = 4
            if (r7 < 0) goto L_0x014d
            long r7 = r0.longValue()     // Catch:{ NumberFormatException -> 0x0173 }
            r9 = 65535(0xffff, double:3.23786E-319)
            int r2 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r2 > 0) goto L_0x014d
            android.util.Pair r0 = new android.util.Pair     // Catch:{ NumberFormatException -> 0x0173 }
            r2 = 3
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ NumberFormatException -> 0x0173 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ NumberFormatException -> 0x0173 }
            r0.<init>(r2, r1)     // Catch:{ NumberFormatException -> 0x0173 }
            return r0
        L_0x014d:
            long r7 = r0.longValue()     // Catch:{ NumberFormatException -> 0x0173 }
            int r0 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r0 >= 0) goto L_0x0165
            android.util.Pair r0 = new android.util.Pair     // Catch:{ NumberFormatException -> 0x0173 }
            r1 = 9
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ NumberFormatException -> 0x0173 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r4)     // Catch:{ NumberFormatException -> 0x0173 }
            r0.<init>(r1, r2)     // Catch:{ NumberFormatException -> 0x0173 }
            return r0
        L_0x0165:
            android.util.Pair r0 = new android.util.Pair     // Catch:{ NumberFormatException -> 0x0173 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ NumberFormatException -> 0x0173 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r4)     // Catch:{ NumberFormatException -> 0x0173 }
            r0.<init>(r1, r2)     // Catch:{ NumberFormatException -> 0x0173 }
            return r0
        L_0x0173:
            java.lang.Double.parseDouble(r11)     // Catch:{ NumberFormatException -> 0x0186 }
            android.util.Pair r11 = new android.util.Pair     // Catch:{ NumberFormatException -> 0x0186 }
            r0 = 12
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ NumberFormatException -> 0x0186 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)     // Catch:{ NumberFormatException -> 0x0186 }
            r11.<init>(r0, r1)     // Catch:{ NumberFormatException -> 0x0186 }
            return r11
        L_0x0186:
            android.util.Pair r11 = new android.util.Pair
            java.lang.Integer r0 = java.lang.Integer.valueOf(r3)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)
            r11.<init>(r0, r1)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.media.ExifInterface.c(java.lang.String):android.util.Pair");
    }

    private void a(int i2, int i3) {
        if (!this.G[i2].isEmpty() && !this.G[i3].isEmpty()) {
            ExifAttribute exifAttribute = (ExifAttribute) this.G[i2].get(TAG_IMAGE_LENGTH);
            ExifAttribute exifAttribute2 = (ExifAttribute) this.G[i2].get(TAG_IMAGE_WIDTH);
            ExifAttribute exifAttribute3 = (ExifAttribute) this.G[i3].get(TAG_IMAGE_LENGTH);
            ExifAttribute exifAttribute4 = (ExifAttribute) this.G[i3].get(TAG_IMAGE_WIDTH);
            if (!(exifAttribute == null || exifAttribute2 == null || exifAttribute3 == null || exifAttribute4 == null)) {
                int b2 = exifAttribute.b(this.H);
                int b3 = exifAttribute2.b(this.H);
                int b4 = exifAttribute3.b(this.H);
                int b5 = exifAttribute4.b(this.H);
                if (b2 < b4 && b3 < b5) {
                    HashMap<String, ExifAttribute> hashMap = this.G[i2];
                    this.G[i2] = this.G[i3];
                    this.G[i3] = hashMap;
                }
            }
        }
    }

    private static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e2) {
                throw e2;
            } catch (Exception unused) {
            }
        }
    }

    private static int b(InputStream inputStream, OutputStream outputStream) {
        byte[] bArr = new byte[8192];
        int i2 = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                return i2;
            }
            i2 += read;
            outputStream.write(bArr, 0, read);
        }
    }

    private static long[] a(Object obj) {
        if (obj instanceof int[]) {
            int[] iArr = (int[]) obj;
            long[] jArr = new long[iArr.length];
            for (int i2 = 0; i2 < iArr.length; i2++) {
                jArr[i2] = (long) iArr[i2];
            }
            return jArr;
        } else if (obj instanceof long[]) {
            return (long[]) obj;
        } else {
            return null;
        }
    }
}
