package com.example.myapplication.slice;

import com.example.myapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.ability.DataAbilityRemoteException;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.TextField;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.ToastDialog;
import ohos.data.dataability.DataAbilityPredicates;
import ohos.data.rdb.ValuesBucket;
import ohos.data.resultset.ResultSet;
import ohos.utils.net.Uri;

public class registerAbilitySlice extends AbilitySlice implements Component.ClickedListener {
    TextField num,pwd,name;
    Button register,back_login;
    private DataAbilityHelper dataAbilityHelper;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_register);
        name=(TextField) findComponentById(ResourceTable.Id_name_r);
        num=(TextField) findComponentById(ResourceTable.Id_num_r);
        pwd=(TextField) findComponentById(ResourceTable.Id_pwd_r);
        back_login=(Button) findComponentById(ResourceTable.Id_back_login);
        register=(Button) findComponentById(ResourceTable.Id_register);
        back_login.setClickedListener(component1 -> present(new loginAbilitySlice(),new Intent()));
        register.setClickedListener(this);
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    @Override
    public void onClick(Component component) {
        if(component==register){
            String nameInput=name.getText();
            String numInput=num.getText();
            String pwdInput=pwd.getText();
            boolean isRegister=false;//判断是否已经注册
            if(numInput.isEmpty()){
                isRegister=true;
                new ToastDialog(this)
                        .setText("请输入你的手机号")
                        .setAlignment(LayoutAlignment.TOP)
                        .show();
            }
            else {
                if(numInput.length()!=11)
                {
                    isRegister=true;
                    new ToastDialog(this)
                            .setText("你的手机号格式错误，不足十一位")
                            .setAlignment(LayoutAlignment.TOP)
                            .show();
                }
                else{
                    if(nameInput.isEmpty()){
                        isRegister=true;
                        new ToastDialog(this)
                                .setText("请输入你的用户名")
                                .setAlignment(LayoutAlignment.TOP)
                                .show();
                    }
                    else {
                        if (pwdInput.isEmpty()) {
                            isRegister=true;
                            new ToastDialog(this)
                                    .setText("请输入你的密码")
                                    .setAlignment(LayoutAlignment.TOP)
                                    .show();
                        }
                        else{
                            dataAbilityHelper=DataAbilityHelper.creator(this);
                            Uri uri=Uri.parse("dataability:///com.example.myapplication.UserDataAbility/users");
                            String[] columns={"userName","userTel","userPwd"};
                            DataAbilityPredicates dataAbilityPredicates=new DataAbilityPredicates();
                            try {
                                ResultSet rs = dataAbilityHelper.query(uri,columns,dataAbilityPredicates);
                                int rowCount=rs.getRowCount();
                                if(rowCount>0){
                                    rs.goToFirstRow();
                                    do{
                                        String userName=rs.getString(0);
                                        String userTel=rs.getString(1);
                                        String userPwd=rs.getString(2);
                                        if(userTel.equals(numInput)){
                                            isRegister=true;
                                            new ToastDialog(this)
                                                    .setText("你的手机号已注册")
                                                    .setAlignment(LayoutAlignment.TOP)
                                                    .show();
                                            break;
                                        }
                                        else {
                                            if(userName.equals(nameInput)){
                                                isRegister=true;
                                                new ToastDialog(this)
                                                        .setText("你的用户名已存在，请换个用户名")
                                                        .setAlignment(LayoutAlignment.TOP)
                                                        .show();
                                                break;
                                            }
                                        }
                                    }while (rs.goToNextRow());
                                }
                            } catch (DataAbilityRemoteException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }

                }

            }
            if (isRegister==false){
                ValuesBucket valuesBucket=new ValuesBucket();
                valuesBucket.putString("userName",nameInput);
                valuesBucket.putString("userTel",numInput);
                valuesBucket.putString("userPwd",pwdInput);

                try {
                    int i=dataAbilityHelper.insert(
                            Uri.parse("dataability:///com.example.myapplication.UserDataAbility/users"),
                            valuesBucket
                    );

                } catch (DataAbilityRemoteException e) {
                    throw new RuntimeException(e);
                }
                new ToastDialog(this)
                        .setText("注册成功")
                        .setAlignment(LayoutAlignment.TOP)
                        .show();
            }
        }
    }
}
