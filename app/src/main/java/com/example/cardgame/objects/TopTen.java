package com.example.cardgame.objects;


import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopTen implements Serializable {
    private final int SIZE = 10;
    private List<Record> records;

    public TopTen() {

        records = new ArrayList<>();
    }

    public TopTen(ArrayList<Record> records) {
        this.records = records;
    }

    public List<Record> getRecords() {
        sortRecords();
        return records;
    }

    public void sortRecords() {
        Collections.sort(records);
    }

    public TopTen setRecords(ArrayList<Record> records) {
        this.records = records;
        return this;
    }

    public Boolean addRecord(Record record) {
        sortRecords();
        if (isFull()) {
            if (checkLast(record) < 0) {
                removeRecord(this.records.get(SIZE - 1));
                this.records.add(record);
                return true;
            }
        } else {
            this.records.add(record);
            return true;
        }
        return false;
    }

    private void removeRecord(Record record) {
        records.remove(SIZE - 1);
    }

    public int checkLast(Record record) {
        return record.compareTo(this.records.get(SIZE - 1));
    }

    public boolean isFull() {
        if (records.size() < SIZE){
            return false;
        }
        return true;

    }
}
