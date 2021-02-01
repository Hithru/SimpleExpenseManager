package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseAssist extends SQLiteOpenHelper {

    static DatabaseAssist instance;

    //Database information
    static final String DB_NAME = "180114D";
    static final int DB_VERSION = 2;

    //account table information
    public static final String TABLE_ACCOUNT ="accounts";
    public static final String ID = "id";
    public static final String ACCOUNT_NO = "account_no";
    public static final String BANK_NAME = "bank_name";
    public static final String ACCOUNT_HOLDER_NAME = "account_holder_name";
    public static final String BALANCE = "balance";

    //account table create command
    private static final String CREATE_TABLE_ACCOUNT = "CREATE TABLE " + TABLE_ACCOUNT + "(" + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ACCOUNT_NO + " TEXT NOT NULL UNIQUE, "
            + BANK_NAME + " TEXT, "
            + ACCOUNT_HOLDER_NAME + " TEXT, "
            + BALANCE + " INTEGER(15) );";


    //transaction table information
    public static final String TABLE_TRANSACTIONS ="transactions";
    public static final String TRANSACTION_ID = "id";
    public static final String TRANSACTION_DATE = "date";
    public static final String TRANSACTION_ACCOUNT_NO = "account_no";
    public static final String EXPENSE_TYPE = "expense_type";
    public static final String AMOUNT = "amount";


    //transaction table Create command
    private static final String CREATE_TABLE_TRANSACTION = "CREATE TABLE " + TABLE_TRANSACTIONS + "(" + TRANSACTION_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TRANSACTION_DATE + " TEXT, "
            + TRANSACTION_ACCOUNT_NO + " TEXT NOT NULL UNIQUE, "
            + EXPENSE_TYPE + " TEXT CHECK( "+EXPENSE_TYPE+" IN ('INCOME','EXPENSE') ), "
            + AMOUNT + " REAL );";


    public DatabaseAssist(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_ACCOUNT);
        sqLiteDatabase.execSQL(CREATE_TABLE_TRANSACTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        onCreate(sqLiteDatabase);
    }

    public synchronized static DatabaseAssist getInstance(Context context) {
        if(instance==null){
            instance = new DatabaseAssist(context);
        }
        return instance;
    }

    public static void closeDB(){
        if(instance!=null){
            instance.close();
        }
    }

}
