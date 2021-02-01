package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistentTransactionDAO implements TransactionDAO {

    private DatabaseAssist dbHelper;
    private Context context;
    private SQLiteDatabase database;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public PersistentTransactionDAO(Context context) {
        this.context = context;
        dbHelper = DatabaseAssist.getInstance(context);
        database = dbHelper.getWritableDatabase();
    }


    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        ContentValues contentValue = new ContentValues();

        //Putting the values to the database
        contentValue.put(DatabaseAssist.TRANSACTION_DATE, dateFormat.format(date));
        contentValue.put(DatabaseAssist.TRANSACTION_ACCOUNT_NO, accountNo);
        contentValue.put(DatabaseAssist.EXPENSE_TYPE, expenseType.name());
        contentValue.put(DatabaseAssist.AMOUNT,amount);
        database.insert(DatabaseAssist.TABLE_TRANSACTIONS, null, contentValue);

    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        String[] columns = new String[]{DatabaseAssist.TRANSACTION_ID,
                DatabaseAssist.TRANSACTION_DATE,
                DatabaseAssist.TRANSACTION_ACCOUNT_NO,
                DatabaseAssist.EXPENSE_TYPE,
                DatabaseAssist.AMOUNT,};
        Cursor cursor = database.query(DatabaseAssist.TABLE_TRANSACTIONS, columns, null, null, null, null, null);
        ArrayList<Transaction> transactions = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                try {
                    transactions.add(new Transaction(dateFormat.parse(cursor.getString(1)),cursor.getString(2), ExpenseType.valueOf(cursor.getString(3)),cursor.getDouble(4)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return transactions;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        String[] columns = new String[]{DatabaseAssist.TRANSACTION_ID,
                DatabaseAssist.TRANSACTION_DATE,
                DatabaseAssist.TRANSACTION_ACCOUNT_NO,
                DatabaseAssist.EXPENSE_TYPE,
                DatabaseAssist.AMOUNT,};
        Cursor cursor = database.query(DatabaseAssist.TABLE_TRANSACTIONS, columns, null, null, null, null, null,String.valueOf(limit));
        ArrayList<Transaction> transactions = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext() ) {
                try {
                    transactions.add(new Transaction(dateFormat.parse(cursor.getString(1)),cursor.getString(2), ExpenseType.valueOf(cursor.getString(3)),cursor.getDouble(4)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return transactions;
    }
}
