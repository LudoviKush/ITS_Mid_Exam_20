package com.example.provabiblioteca2020.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LibriMovimentiProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.provabiblioteca2020.LibriMovimentiProvider";
    public static final String BASE_PATH_LIBROS = "libros";
    public static final String BASE_PATH_MOVIMENTOS = "movimentos";
    public static final int ALL_LIBRO = 0;
    public static final int SINGLE_LIBRO = 1;
    public static final int ALL_MOVIMENTO = 2;
    public static final int SINGLE_MOVIMENTO = 3;

    public static final String MIME_TYPE_LIBROS = ContentResolver.CURSOR_DIR_BASE_TYPE + "vnd.all_libros";
    public static final String MIME_TYPE_LIBRO = ContentResolver.CURSOR_ITEM_BASE_TYPE + "vnd.single_libro";
    public static final String MIME_TYPE_MOVIMENTOS = ContentResolver.CURSOR_DIR_BASE_TYPE + "vnd.all_movimentos";
    public static final String MIME_TYPE_MOVIMENTO = ContentResolver.CURSOR_ITEM_BASE_TYPE + "vnd.single_movimento";

    public static final Uri LIBROS_URI = Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY + "/" + BASE_PATH_LIBROS);
    public static final Uri MOVIMENTOS_URI = Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY + "/" + BASE_PATH_MOVIMENTOS);

    private Database mDbHelper;

    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(AUTHORITY, BASE_PATH_LIBROS, ALL_LIBRO);
        mUriMatcher.addURI(AUTHORITY, BASE_PATH_LIBROS + "/#", SINGLE_LIBRO);
        mUriMatcher.addURI(AUTHORITY, BASE_PATH_MOVIMENTOS, ALL_MOVIMENTO);
        mUriMatcher.addURI(AUTHORITY, BASE_PATH_MOVIMENTOS + "/#", SINGLE_MOVIMENTO);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new Database(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase vDb = mDbHelper.getReadableDatabase();
        SQLiteQueryBuilder vBuilder = new SQLiteQueryBuilder();

        switch (mUriMatcher.match(uri)) {
            case SINGLE_LIBRO:
                vBuilder.setTables(LibriTableHelper.TABLE_NAME);
                vBuilder.appendWhere(LibriTableHelper._ID + " = " + uri.getLastPathSegment());
                break;

            case ALL_LIBRO:
                vBuilder.setTables(LibriTableHelper.TABLE_NAME);
                break;

            case SINGLE_MOVIMENTO:
                vBuilder.setTables(MovimentiTableHelper.TABLE_NAME);
                vBuilder.appendWhere(MovimentiTableHelper._ID + " = " + uri.getLastPathSegment());
                break;

            case ALL_MOVIMENTO:
                vBuilder.setTables(MovimentiTableHelper.TABLE_NAME);
                break;
        }

        Cursor vCursor = vBuilder.query(vDb, projection, selection, selectionArgs, null, null, sortOrder);
        vCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return vCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (mUriMatcher.match(uri)) {
            case SINGLE_LIBRO:
                return MIME_TYPE_LIBRO;
            case ALL_LIBRO:
                return MIME_TYPE_LIBROS;
            case SINGLE_MOVIMENTO:
                return MIME_TYPE_MOVIMENTO;
            case ALL_MOVIMENTO:
                return MIME_TYPE_MOVIMENTOS;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase vDb = mDbHelper.getWritableDatabase();
        long vResult = 0;

        String vResultString = "";

        switch (mUriMatcher.match(uri)) {
            case ALL_LIBRO:
                vResult = vDb.insert(LibriTableHelper.TABLE_NAME, null, values);
                vResultString = ContentResolver.SCHEME_CONTENT + "://" + BASE_PATH_LIBROS + "/" + vResult;
                break;

            case ALL_MOVIMENTO:
                vResult = vDb.insert(MovimentiTableHelper.TABLE_NAME, null, values);
                vResultString = ContentResolver.SCHEME_CONTENT + "://" + BASE_PATH_MOVIMENTOS + "/" + vResult;
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        Uri vResultUri = Uri.parse(uri.toString() + "/" + vResultString);
        return vResultUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase vDb = mDbHelper.getWritableDatabase();
        String vTableName = "";
        String vQuery = "";

        switch (mUriMatcher.match(uri)) {
            case SINGLE_LIBRO:
                vTableName = LibriTableHelper.TABLE_NAME;
                vQuery = LibriTableHelper._ID + " = " + uri.getLastPathSegment();
                if (selection != null) {
                    vQuery += " AND " + selection;
                }
                break;

            case ALL_LIBRO:
                vTableName = LibriTableHelper.TABLE_NAME;
                vQuery = selection;
                break;

            case SINGLE_MOVIMENTO:
                vTableName = MovimentiTableHelper.TABLE_NAME;
                vQuery = MovimentiTableHelper._ID + " = " + uri.getLastPathSegment();
                if (selection != null) {
                    vQuery += " AND " + selection;
                }
                break;

            case ALL_MOVIMENTO:
                vTableName = MovimentiTableHelper.TABLE_NAME;
                vQuery = selection;
                break;

        }
        int vDeletedRows = vDb.delete(vTableName, vQuery, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return vDeletedRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase vDb = mDbHelper.getWritableDatabase();
        String vTableName = "";
        String vQuery = "";

        switch (mUriMatcher.match(uri)) {
            case SINGLE_LIBRO:
                vTableName = LibriTableHelper.TABLE_NAME;
                vQuery = LibriTableHelper._ID + " = " + uri.getLastPathSegment();
                if (selection != null) {
                    vQuery += " AND " + selection;
                }
                break;

            case ALL_LIBRO:
                vTableName = LibriTableHelper.TABLE_NAME;
                vQuery = selection;
                break;

            case SINGLE_MOVIMENTO:
                vTableName = MovimentiTableHelper.TABLE_NAME;
                vQuery = MovimentiTableHelper._ID + " = " + uri.getLastPathSegment();
                if (selection != null) {
                    vQuery += " AND " + selection;
                }
                break;

            case ALL_MOVIMENTO:
                vTableName = MovimentiTableHelper.TABLE_NAME;
                vQuery = selection;
                break;

        }
        int vUpdatedRows = vDb.update(vTableName, values, vQuery, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return vUpdatedRows;
    }
}