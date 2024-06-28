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
import ohos.app.Context;
import ohos.data.DatabaseHelper;
import ohos.data.dataability.DataAbilityPredicates;
import ohos.data.preferences.Preferences;
import ohos.data.resultset.ResultSet;
import ohos.utils.net.Uri;

public class loginAbilitySlice extends AbilitySlice implements Component.ClickedListener {
    TextField num,pwd;
    Button login,register;
    private DataAbilityHelper dataAbilityHelper;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        num=(TextField) findComponentById(ResourceTable.Id_num);
        pwd=(TextField) findComponentById(ResourceTable.Id_pwd);
        login=(Button) findComponentById(ResourceTable.Id_loginBtn);
        register=(Button) findComponentById(ResourceTable.Id_registerBtn);

        login.setClickedListener(this);
        register.setClickedListener(component -> present(new registerAbilitySlice(),new Intent()));

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
        if(component==login){
            String numInput=num.getText();
            String pwdInput=pwd.getText();
            boolean isRegister=false;//判断是否已经注册
            if(numInput.isEmpty()){
                new ToastDialog(this)
                        .setText("请输入你的手机号")
                        .setAlignment(LayoutAlignment.TOP)
                        .show();
            }
            else {
                if (pwdInput.isEmpty()) {
                    new ToastDialog(this)
                            .setText("请输入你的密码")
                            .setAlignment(LayoutAlignment.TOP)
                            .show();
                }
                else{
                    dataAbilityHelper=DataAbilityHelper.creator(this);
                    Uri uri=Uri.parse("dataability:///com.example.myapplication.UserDataAbility/users");
                    String[] columns={"userTel","userPwd","userId"};
                    DataAbilityPredicates dataAbilityPredicates=new DataAbilityPredicates();
                    try {
                        ResultSet rs = dataAbilityHelper.query(uri,columns,dataAbilityPredicates);
                        int rowCount=rs.getRowCount();
                        if(rowCount>0){
                            rs.goToFirstRow();
                            do{
                                String userTel=rs.getString(0);
                                String userPwd=rs.getString(1);
                                String userId=rs.getString(2);
                                if(!userTel.equals(numInput)){
                                    new ToastDialog(this)
                                            .setText("你的账户不存在")
                                            .setAlignment(LayoutAlignment.TOP)
                                            .show();
                                }
                                else {
                                    if(!userPwd.equals(pwdInput)){
                                        new ToastDialog(this)
                                                .setText("你的密码错误")
                                                .setAlignment(LayoutAlignment.TOP)
                                                .show();
                                    }
                                    else {
                                        isRegister= true;
                                        Context context = getContext(); // 数据文件存储路径：/data/data/{PackageName}/{AbilityName}/preferences。
                                        DatabaseHelper databaseHelper = new DatabaseHelper(context); // context入参类型为ohos.app.Context。
                                        String fileName = "user_tel"; // fileName表示文件名，其取值不能为空，也不能包含路径，默认存储目录可以通过context.getPreferencesDir()获取。
                                        Preferences preferences = databaseHelper.getPreferences(fileName);
                                        preferences.putString("User_tel", userTel);
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
            if(isRegister==true)
            {
                present(new shoppingMainAbilitySlice(),new Intent());
            }
        }
    }
}
