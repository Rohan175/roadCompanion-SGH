package com.rsking175453.com.sgh_try1;

    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.util.Log;

    import com.rsking175453.com.sgh_try1.models.SingleItemModel;

    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Date;

public class databsehandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SGH.db";
    public static final String TABLE_NAME = "ROAD_COMPLAINTS";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_CURR_OFFICER_ID = "OFFICER_ID";
    public static final String COLUMN_URL = "URL";
    public static final String COLUMN_LAT = "LOCATION_LATITUDE";
    public static final String COLUMN_LONG = "LOCATION_LONGITUDE";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_GRIEVANCE_TYPE = "GRIEVANCE_TYPE";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_COMPLAINiD = "CID";
    public static final String COLUMN_IS_EMERGENCY = "ISEMERGENCY";
    public static final String COLUMN_WORK_STAT = "WORK_STATUS";
    public static final String COLUMN_CURR_OFFICER = "CURRENT_OFFICER";
    public static final String COLUMN_TIME = "TIME";
    public static final String COLUMN_COMMENTS = "COMMENTS";
    public static final String COLUMN_ETA = "ESTIMATED_TIME";
    private SQLiteDatabase database;


    public databsehandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createQuery = " create table " + TABLE_NAME + " ( "
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_URL + " VARCHAR , "
                + COLUMN_DESCRIPTION + " VARCHAR , "
                + COLUMN_LONG + " VARCHAR , "
                + COLUMN_LAT + " VARCHAR , "
                + COLUMN_GRIEVANCE_TYPE + " VARCHAR , "
                + COLUMN_WORK_STAT + " VARCHAR , "
                + COLUMN_CURR_OFFICER_ID+ " INTEGER , "
                + COLUMN_CURR_OFFICER + " VARCHAR , "
                + COLUMN_IS_EMERGENCY + " INTEGER , "
                + COLUMN_TIME + " DATE , "
                + COLUMN_COMMENTS + " VARCHAR , "
                + COLUMN_COMPLAINiD + " VARCHAR , "
                + COLUMN_EMAIL + " VARCHAR , "
                + COLUMN_ETA + " DATE);";
        sqLiteDatabase.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void delete(){
        database=getWritableDatabase();
        database.execSQL(" DELETE FROM  "+ TABLE_NAME);
        database.close();
    }

    public void insertRecord(SingleItemModel contactModel) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_URL, contactModel.getUrl());
        contentValues.put(COLUMN_DESCRIPTION, contactModel.getDiscription());
        contentValues.put(COLUMN_LAT, contactModel.getLocationLat());
        contentValues.put(COLUMN_LONG, contactModel.getLocationLong());
        contentValues.put(COLUMN_GRIEVANCE_TYPE, contactModel.getGrivType());
        contentValues.put(COLUMN_WORK_STAT, contactModel.getStatus());
        contentValues.put(COLUMN_CURR_OFFICER, contactModel.getOfficerName());
        contentValues.put(COLUMN_CURR_OFFICER_ID, contactModel.getOfficerId());
        contentValues.put(COLUMN_IS_EMERGENCY, contactModel.getIsEmergency());
        contentValues.put(COLUMN_TIME, String.valueOf(contactModel.getTime()));
        contentValues.put(COLUMN_COMMENTS, contactModel.getComment());
        contentValues.put(COLUMN_COMPLAINiD, contactModel.getComplainId());
        contentValues.put(COLUMN_EMAIL, contactModel.getEmail());
        contentValues.put(COLUMN_ETA, String.valueOf(contactModel.getEstimatedTime()));
        database.insert(TABLE_NAME, null, contentValues);
        database.close();
    }

    public ArrayList<SingleItemModel> getAllRecords() {
        database = this.getReadableDatabase();
        ArrayList<SingleItemModel> arrayList = new ArrayList<SingleItemModel>();
        SingleItemModel contactModel;
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                contactModel = new SingleItemModel();
                contactModel.setId(cursor.getString(0));
                contactModel.setUrl(cursor.getString(1));
                contactModel.setDiscription(cursor.getString(2));
                contactModel.setLocationLat(cursor.getString(4));
                contactModel.setLocationLong(cursor.getString(3));
                contactModel.setGrivType(cursor.getString(5));
                contactModel.setStatus(cursor.getString(6));
                contactModel.setOfficerName(cursor.getString(7));
                contactModel.setOfficerId(cursor.getString(8));
                contactModel.setIsEmergency(Integer.parseInt(cursor.getString(9)));
                contactModel.setTime(cursor.getString(10));
                contactModel.setComment(cursor.getString(11));
                contactModel.setComplainId(cursor.getString(12));
                contactModel.setEmail(cursor.getString(13));
                contactModel.setEstimatedTime(cursor.getString(14));
                arrayList.add(contactModel);
            }
        }
        cursor.close();
        database.close();
        return arrayList;
    }

    //    //public void updateRecord(ContactModel contactModel) {
//        database=getReadableDatabase();
//        ContentValues contentValues=new ContentValues();
//        contentValues.put(COLUMN_TABLE_ID, contactModel.getTableID());
//        contentValues.put(COLUMN_URL, contactModel.getUrl());
//        contentValues.put(COLUMN_LAT, contactModel.getLatitude());
//        contentValues.put(COLUMN_LONG, contactModel.getLongiude());
//        contentValues.put(COLUMN_PRIORITY, contactModel.getPriority());
//        contentValues.put(COLUMN_DESCRIPTION, contactModel.getDescription());
//        contentValues.put(COLUMN_ROAD_TYPE, contactModel.getRoadType());
//        contentValues.put(COLUMN_GRIEVANCE_TYPE, contactModel.getGrievanceType());
//        contentValues.put(COLUMN_AREA, contactModel.getArea());
//        contentValues.put(COLUMN_CITY, contactModel.getCity());
//        contentValues.put(COLUMN_STATE, contactModel.getState());
//        contentValues.put(COLUMN_IS_EMERGENCY, contactModel.getIsEmergency());
//        contentValues.put(COLUMN_WORK_STAT, contactModel.getWorkStatus());
//        contentValues.put(COLUMN_CURR_OFFICER, contactModel.getCurrOfficer());
//        contentValues.put(COLUMN_TIME, contactModel.getTime());
//        contentValues.put(COLUMN_COMMENTS, contactModel.getComments());
//        contentValues.put(COLUMN_ETA, contactModel.getEta());
//        database.update(TABLE_NAME,contentValues,COLUMN_ID + " = ?",new String[]{contactModel.getID()});
//        database.close();
//    }
    public void deleteRecord(SingleItemModel contactModel) {
        database = getReadableDatabase();
        database.delete(TABLE_NAME, COLUMN_ID + " = ? ", new String[]{contactModel.getId()});
        database.close();
    }
}