package com.changgou.search.controller;

import com.changgou.goods.pojo.Sku;
import com.changgou.search.feign.SkuFeign;
import com.changgou.search.pojo.SkuInfo;
import entity.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

/**
 * @Author WEN
 * @Date 2021/1/10 13:29
 */
@Controller
@RequestMapping(value = "/search")
public class SkuController {

    @Autowired
    private SkuFeign skuFeign;

    /**
     * 实现搜索调用
     * @param searchMap
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/list")
    public String search(@RequestParam(required = false) Map<String, String> searchMap , Model model) throws Exception {
        System.out.println("关键词搜索："+searchMap.get("keywords"));
        System.out.println("分类搜索："+searchMap.get("category"));
        System.out.println("品牌搜索："+searchMap.get("brand"));
        String keyswords = "华为";
        if (searchMap != null && !StringUtils.isEmpty(searchMap.get("keywords"))){
            keyswords = "<em style='color:red'>"+searchMap.get("keywords")+"</em>";
        }

        // 调用搜索微服务
//        Map<String, Object> resultMap = skuFeign.search(searchMap);
        // 封装模拟数据
        Map<String, Object> resultMap = new HashMap<>();
        List<SkuInfo> skuInfoList = new ArrayList<>();
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setName(keyswords + "是一款高档手机，全球第一红色 移动5G全球第一红色 移动5G全球第一红色 移动5G全球第一红色 移动5G全球第一红色 移动5G全球第一红色 移动5G全球第一红色 移动5G全球第一红色 移动5G全球第一红色 移动5G");
        skuInfo.setPrice(60887L);
        skuInfo.setImage("/img/_/ic_icon.png");
        skuInfoList.add(skuInfo);
        resultMap.put("rows", skuInfoList);
        // 分类数据封装
        List<String> categoryList = new ArrayList<>();
        categoryList.add("手机配件");
        categoryList.add("户外工具");
        categoryList.add("笔记本");
        categoryList.add("平板电脑");
        categoryList.add("语言文字");
        resultMap.put("categoryList", categoryList);
        // 封装品牌
        List<String> brandList = new ArrayList<>();
        brandList.add("华为");
        brandList.add("守护宝");
        brandList.add("小米");
        brandList.add("手机配件");
        brandList.add("TCL");
        brandList.add("联想");
        resultMap.put("brandList", brandList);
        // 规格封装
        Map<String, Set<String>> specMap = new HashMap<>();
        Set<String> specSet1 = new HashSet<>();
        specSet1.add("联通3G");
        specSet1.add("联通4G");
        specSet1.add("电信4G");
        specMap.put("网络", specSet1);
        Set<String> specSet2 = new HashSet<>();
        specSet2.add("红");
        specSet2.add("紫");
        specMap.put("颜色", specSet2);
        Set<String> specSet3 = new HashSet<>();
        specSet3.add("16G");
        specSet3.add("128G");
        specMap.put("机身内存", specSet3);
        Set<String> specSet4 = new HashSet<>();
        specSet4.add("16G");
        specSet4.add("128G");
        specMap.put("存储", specSet4);
        Set<String> specSet5 = new HashSet<>();
        specSet5.add("300万像素");
        specSet5.add("800万像素");
        specMap.put("存储", specSet5);

        resultMap.put("specList", specMap);
        model.addAttribute("result", resultMap);

        // 分页页码
        int pageNum = null != searchMap && !StringUtils.isEmpty(searchMap.get("pageNum"))
                && Integer.parseInt(searchMap.get("pageNum")) > 0 ? Integer.parseInt(searchMap.get("pageNum"))-1 : 0;
        resultMap.put("totalPages", 1040);
        resultMap.put("pageSize", 30);
        resultMap.put("pageNumber", pageNum);
        //计算分页
        Page<SkuInfo> pageInfo = new Page<>(
                Integer.parseInt(resultMap.get("totalPages").toString()),
                Integer.parseInt(resultMap.get("pageNumber").toString())+1,
                Integer.parseInt(resultMap.get("pageSize").toString())
        );
        System.out.println(">>>>>>>>>"+pageInfo.toString());
        model.addAttribute("pageInfo", pageInfo);

        // 将条件存储，用于页面回显数据
        model.addAttribute("searchMap", searchMap);
        // 获取上次请求地址
        String[] urls = url(searchMap);
        model.addAttribute("url", urls[0]);
        model.addAttribute("sortUrl", urls[1]);
        return "search";
    }


    /**
     * 拼接组装用户请求的URL地址
     * 获取用户每次请求的地址
     * 页面需要在这次请求的地址上面添加额外的搜索条件
     * http://127.0.0.1:18086/search/list?keywords=华为
     * http://127.0.0.1:18086/search/list?keywords=华为&brand=华为
     * @param searchMap
     * @return
     */
    public String[] url(Map<String, String> searchMap){
        String url = "/search/list"; // 初始化地址
        String sortUrl = "/search/list"; // 排序地址
        if (searchMap != null && searchMap.size() > 0){
            url += "?";
            sortUrl += "?";
            for (Map.Entry<String, String> entry : searchMap.entrySet()) {
                // key 是搜索的条件对象
                String key = entry.getKey();

                // 跳过分页参数
                if (key.equalsIgnoreCase("pageNum")){
                    continue;
                }

                // value是搜索的值
                String value = entry.getValue();
                url += key+"="+value+"&";
                // 排序参数，跳过
                if (key.equalsIgnoreCase("sortField") || key.equalsIgnoreCase("sortRule")){
                    continue;
                }
                sortUrl += key+"="+value+"&";
            }
            url = url.substring(0, url.length()-1);
            sortUrl = sortUrl.substring(0, sortUrl.length()-1);
        }
        return new String[]{url, sortUrl};
    }
}
