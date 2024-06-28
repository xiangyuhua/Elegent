package com.example.myapplication.slice;

import com.example.myapplication.ResourceTable;
import com.example.myapplication.provider.MyItemProvider;
import com.example.myapplication.adapter.PageItemAdapter;
import com.example.myapplication.entity.Page;
import com.example.myapplication.entity.img_item;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.ability.DataAbilityRemoteException;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.data.rdb.ValuesBucket;
import ohos.utils.net.Uri;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class shoppingMainAbilitySlice extends AbilitySlice {
    private int[] images = {
            ResourceTable.Media_coser_1,
            ResourceTable.Media_coser_2,
            ResourceTable.Media_coser_3,
            };
    private List<Page> pages=new ArrayList<>();
    private void initPage() {
        for (int i = 0; i < images.length; i++) {
            Page page=new Page(images[i]);
            pages.add(page);
        }
    }
    private PageItemAdapter adapter;
    TextField searchText;
    Button home,cart,self,search;
    List<img_item> list;
    private DataAbilityHelper dataAbilityHelper;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_shoping_main);

//        dataAbilityHelper= DataAbilityHelper.creator(this);
//        ValuesBucket vb1 = new ValuesBucket();
//        vb1.putInteger("clothId", 1);
//        vb1.putString("clothName", "鬼滅之刃鬼殺隊設計款cosplay全套裝");
//        vb1.putString("clothPrice", "188");
//        vb1.putInteger("clothImg", ResourceTable.Media_coser_1);
//
//        ValuesBucket vb2 = new ValuesBucket();
//        vb2.putInteger("clothId", 2);
//        vb2.putString("clothName", "咒術迴戰造型咒術高專制服cosplay套裝");
//        vb2.putString("clothPrice", "233");
//        vb2.putInteger("clothImg", ResourceTable.Media_coser_2);
//
//
//        ValuesBucket vb3 = new ValuesBucket();
//        vb3.putInteger("clothId", 3);
//        vb3.putString("clothName", "進擊的巨人調查兵團設計款短版制服");
//        vb3.putString("clothPrice", "166");
//        vb3.putInteger("clothImg", ResourceTable.Media_coser_3);
//
//        ValuesBucket vb4 = new ValuesBucket();
//        vb4.putInteger("clothId", 4);
//        vb4.putString("clothName", "肌肉魔法使马修COSPLAY魔法使袍");
//        vb4.putString("clothPrice", "199");
//        vb4.putInteger("clothImg", ResourceTable.Media_coser_4);
//
//        ValuesBucket vb5 = new ValuesBucket();
//        vb5.putInteger("clothId", 5);
//        vb5.putString("clothName", "电锯人链锯人支配恶魔玛奇玛真纪真辫子粉色浏海假发");
//        vb5.putString("clothPrice", "120");
//        vb5.putInteger("clothImg", ResourceTable.Media_coser_5);
//
//        ValuesBucket vb6 = new ValuesBucket();
//        vb6.putInteger("clothId", 6);
//        vb6.putString("clothName", "电锯人链锯人恶魔猎人早川秋浏海发髻假发");
//        vb6.putString("clothPrice", "111");
//        vb6.putInteger("clothImg", ResourceTable.Media_coser_6);
//        try {
//            int i=dataAbilityHelper.insert(
//                    Uri.parse("dataability:///com.example.myapplication.commodityDataAbility/cloth"),
//                    vb1
//            );
//            i=dataAbilityHelper.insert(
//                    Uri.parse("dataability:///com.example.myapplication.commodityDataAbility/cloth"),
//                    vb2
//            );
//            i=dataAbilityHelper.insert(
//                    Uri.parse("dataability:///com.example.myapplication.commodityDataAbility/cloth"),
//                    vb3
//            );
//            i=dataAbilityHelper.insert(
//                    Uri.parse("dataability:///com.example.myapplication.commodityDataAbility/cloth"),
//                    vb4
//            );
//            i=dataAbilityHelper.insert(
//                    Uri.parse("dataability:///com.example.myapplication.commodityDataAbility/cloth"),
//                    vb5
//            );
//            i=dataAbilityHelper.insert(
//                    Uri.parse("dataability:///com.example.myapplication.commodityDataAbility/cloth"),
//                    vb6
//            );
//
//        } catch (DataAbilityRemoteException e) {
//            throw new RuntimeException(e);
//        }

        home=(Button) findComponentById(ResourceTable.Id_homePageBtn1);
        cart=(Button) findComponentById(ResourceTable.Id_cartBtn2);
        self=(Button) findComponentById(ResourceTable.Id_selfBtn3);
        search=(Button) findComponentById(ResourceTable.Id_searchBtn);
        searchText = (TextField) findComponentById(ResourceTable.Id_searchText);

        home.setClickedListener(component->present(new shoppingMainAbilitySlice(),new Intent()));
        cart.setClickedListener(component->present(new cartAbilitySlice(),new Intent()));
        self.setClickedListener(component->present(new selfAbilitySlice(),new Intent()));


        PageSlider ps = (PageSlider) this.findComponentById(ResourceTable.Id_page_slider);
        //初始化图片和文字数据封装在集合中
        initPage();
        //创建适配器对象，将当前界面对象和封装好的集合发送过去
        adapter=new PageItemAdapter(pages,this);
        //将适配器加载至滑动组件上，完成同步组装
        ps.setProvider(adapter);

        list = getData();
        MyItemProvider myItemProvider = new MyItemProvider(list, this);
        ListContainer listContainer = (ListContainer) findComponentById(ResourceTable.Id_lc1);
        listContainer.setItemProvider(myItemProvider);
        listContainer.setItemClickedListener(new ListContainer.ItemClickedListener() {
            @Override
            public void onItemClicked(ListContainer listContainer, Component component, int i, long l) {
                img_item item=(img_item) listContainer.getItemProvider().getItem(i);
                int clothId=item.getId();
                present(new commodityDetailAbilitySlice(),new Intent().setParam("clothId",clothId));
            }
        });

        search.setClickedListener(component->performSearch());
    }

    private List<img_item> getData() {
        List<img_item> list = new ArrayList<>();
        list.add(new img_item("鬼滅之刃鬼殺隊設計款cosplay全套裝","188",ResourceTable.Media_coser_1,1));
        list.add(new img_item("咒術迴戰造型咒術高專制服cosplay套裝","233",ResourceTable.Media_coser_2,2));
        list.add(new img_item("進擊的巨人調查兵團設計款短版制服","166",ResourceTable.Media_coser_3,3));
        list.add(new img_item("肌肉魔法使马修COSPLAY魔法使袍","199",ResourceTable.Media_coser_4,4));
        list.add(new img_item("电锯人链锯人支配恶魔玛奇玛真纪真辫子粉色浏海假发","120",ResourceTable.Media_coser_5,5));
        list.add(new img_item("电锯人链锯人恶魔猎人早川秋浏海发髻假发","111",ResourceTable.Media_coser_6,6));
        return list;
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
    private void performSearch() {
        String keyword = searchText.getText();
        List<img_item> filteredList = list.stream()
                .filter(item -> item.getName().contains(keyword))
                .collect(Collectors.toList());

        Intent intent = new Intent();
        intent.setParam("searchResult", new ArrayList<>(filteredList));
        present(new searchResultAbilitySlice(), intent);
    }


}
