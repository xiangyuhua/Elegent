package com.example.myapplication;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.data.DatabaseHelper;
import ohos.data.dataability.DataAbilityUtils;
import ohos.data.rdb.*;
import ohos.data.resultset.ResultSet;
import ohos.data.dataability.DataAbilityPredicates;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.rpc.MessageParcel;
import ohos.utils.net.Uri;
import ohos.utils.PacMap;

import java.io.*;

public class commodityDataAbility extends Ability {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "Demo");
    private RdbStore rdbStore;
    StoreConfig config=StoreConfig.newDefaultConfig("commodityStore.db");
    RdbOpenCallback callback=new RdbOpenCallback() {
        @Override
        public void onCreate(RdbStore rdbStore) {
            rdbStore.executeSql("create table if not exists cloth(" +
                    "clothId integer primary key autoincrement," +
                    "clothName text not null ," +
                    "clothImg text  not null," +
                    "clothPrice text not null )" );
        }

        @Override
        public void onUpgrade(RdbStore rdbStore, int i, int i1) {

        }
    };
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        HiLog.info(LABEL_LOG, "commodityDataAbility onStart");

        //数据库链接
        DatabaseHelper helper=new DatabaseHelper(this);
        rdbStore= helper.getRdbStore(config,1,callback);
    }

    @Override
    public ResultSet query(Uri uri, String[] columns, DataAbilityPredicates predicates) {
        RdbPredicates rdbPredicates= DataAbilityUtils.createRdbPredicates(predicates,"cloth");
        ResultSet rs=rdbStore.query(rdbPredicates,columns);
        return rs;
    }

    @Override
    public int insert(Uri uri, ValuesBucket value) {
        HiLog.info(LABEL_LOG, "commodityDataAbility insert");
        int i=-1;

        String path=uri.getLastPath();
        if ("cloth".equalsIgnoreCase(path)){
            i=(int) rdbStore.insert("cloth",value);
        }
        return i;
    }

    @Override
    public int delete(Uri uri, DataAbilityPredicates predicates) {
        RdbPredicates rdbPredicates=DataAbilityUtils.createRdbPredicates(predicates,"cloth");
        int i=rdbStore.delete(rdbPredicates);

        return i;
    }

    @Override
    public int update(Uri uri, ValuesBucket value, DataAbilityPredicates predicates) {

        RdbPredicates rdbPredicates=DataAbilityUtils.createRdbPredicates(predicates,"cloth");
        int i=rdbStore.update(value,rdbPredicates);

        return i;
    }

    @Override
    public FileDescriptor openFile(Uri uri, String mode)throws FileNotFoundException {
        File file=new File((uri.getDecodedPathList().get(0)));
        if(mode==null||!"rw".equals(mode)){
            file.setReadOnly();
        }
        FileInputStream fileIs=new FileInputStream(file);
        FileDescriptor fd=null;
        try {
            fd=fileIs.getFD();
        } catch (IOException e) {
            HiLog.info(LABEL_LOG, "failed to GetFD");
        }
        return MessageParcel.dupFileDescriptor(fd);
    }


    @Override
    public String[] getFileTypes(Uri uri, String mimeTypeFilter) {
        return new String[0];
    }

    @Override
    public PacMap call(String method, String arg, PacMap extras) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}