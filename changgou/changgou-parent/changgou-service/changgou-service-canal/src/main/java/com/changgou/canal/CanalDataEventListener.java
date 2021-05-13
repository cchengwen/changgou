package com.changgou.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.xpand.starter.canal.annotation.*;

/**
 * 实现Mysql数据监听
 * @Author WEN
 * @Date 2020/12/9 22:20
 */
@CanalEventListener
public class CanalDataEventListener {

    /**
     * <p>
     *     @InsertListenPoint: 增加监听，只有增加后的数据
     *     rowData.getAfterColumnsList()：增加、修改
     *     rowData.getBeforeColumnsList()：删除、修改
     * </p>
     * @param eventType 当前操作的类型，如：增加数据
     * @param rowData 发生变更的一行数据
     */
    @InsertListenPoint
    public void onEventInsert(CanalEntry.EventType eventType, CanalEntry.RowData rowData){
        for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
            System.out.println("列名：" + column.getName() + " ------------>>> 变更的数据：" + column.getValue());
        }
    }

    /**
     * <p>
     *     @UpdateListenPoint: 修改监听
     *     rowData.getAfterColumnsList()：增加、修改
     *     rowData.getBeforeColumnsList()：删除、修改
     * </p>
     * @param eventType 当前操作的类型，如：增加数据
     * @param rowData 发生变更的一行数据
     */
    @UpdateListenPoint
    public void onEventUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData){
        for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
            System.out.println("修改前：列名：" + column.getName() + " ------------>>> 变更的数据：" + column.getValue());
        }
        for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
            System.out.println("修改后：列名：" + column.getName() + " ------------>>> 变更的数据：" + column.getValue());
        }
    }

    /**
     * <p>
     *     @DeleteListenPoint: 删除监听
     *     rowData.getAfterColumnsList()：增加、修改
     *     rowData.getBeforeColumnsList()：删除、修改
     * </p>
     * @param eventType 当前操作的类型，如：增加数据
     * @param rowData 发生变更的一行数据
     */
    @DeleteListenPoint
    public void onEventDelete(CanalEntry.EventType eventType, CanalEntry.RowData rowData){
        for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
            System.out.println("删除前：列名：" + column.getName() + " ------------>>> 变更的数据：" + column.getValue());
        }
    }

    /**
     * <p>
     *  自定义监听
     *     rowData.getAfterColumnsList()：增加、修改
     *     rowData.getBeforeColumnsList()：删除、修改
     * </p>
     * @param eventType 当前操作的类型，如：增加数据
     * @param rowData 发生变更的一行数据
     */
    @ListenPoint(
            eventType = {CanalEntry.EventType.DELETE, CanalEntry.EventType.UPDATE, CanalEntry.EventType.INSERT}, // 监听类型
            schema = {"changgou-content"}, // 指定监听数据库
            table = {"tb_content"},  // 指定监听的表
            destination = "example" // 指定实例的地址（注：在虚拟机配置Canal的地址一致）
    )
    public void onEventCustomUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData){
        for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
            System.out.println("自定义操作前：列名：" + column.getName() + " ------------>>> 变更的数据：" + column.getValue());
        }
        for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
            System.out.println("自定义操作后：列名：" + column.getName() + " ------------>>> 变更的数据：" + column.getValue());
        }
    }
}
