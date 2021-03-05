package com.example.graduation_design.service;

import com.example.graduation_design.bean.*;
import com.example.graduation_design.repository.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderService {
    @Resource
    private ShoppingOrderRepository shoppingOrderRepository;
    @Resource
    private ShoppingCartItemRepository shoppingCartItemRepository;
    @Resource
    private OrderItemRepository orderItemRepository;
    @Resource
    private ReceiptsAddressRepository receiptsAddressRepository;
@Resource
private BusinessRepository businessRepository;

    //前台：生成订单，并修改购物车得购物清单项目,返回订单的id，方便后面支付成功修改
    public int addOrder(String orderNumber, int receiptsAddress, int[] itemIds, String message, User user){
        ShoppingCartItem shoppingCartItem;
        OrderItem orderItem;
        ShoppingOrder shoppingOrder=new ShoppingOrder();
        List<OrderItem> orderItems=new ArrayList<>();
        float sum=0;        //计算订单的总价
        for (int itemId:itemIds) {
            orderItem=new OrderItem();
            shoppingCartItem=shoppingCartItemRepository.findById(itemId).get();
            sum+=shoppingCartItem.getBook().getPrice()*shoppingCartItem.getBookNumber();        //计算订单的总价
            orderItem.setBook(shoppingCartItem.getBook());
            orderItem.setoINumber(shoppingCartItem.getBookNumber());//标记是哪个商家的
            orderItem.setIsPayment(0);// 是否支付 0为未支付
            orderItem.setBusiness(shoppingCartItem.getBook().getBusiness());
            orderItemRepository.save(orderItem);
            //修改购物车条目的有效值，0为无效值
            shoppingCartItemRepository.updateShoppingCartItemIsValidation(0,shoppingCartItem.getShoppingCartItemId());
            orderItems.add(orderItem);
            orderItem.setShoppingOrder(shoppingOrder);
        }
        shoppingOrder.setOrderItems(orderItems);     //添加订单条目
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String createDate=formatter.format(date);
        shoppingOrder.setIsPayment(0);
        shoppingOrder.setCreateDate(createDate);     //创建的时间
        shoppingOrder.setOrderNumber(orderNumber);      //订单编号
        shoppingOrder.setTotalPrice(sum);       //订单总价
        shoppingOrder.setUser(user);        //订单所属的用户
        shoppingOrder.setMessage(message);      //订单的备注
        shoppingOrder.setShipping(10);      //订单的运费，暂且为10，
        shoppingOrder.setReceiptsAddress(receiptsAddressRepository.findById(receiptsAddress).get());        //订单的收货地址和联系人
        ShoppingOrder shoppingOrder2=shoppingOrderRepository.save(shoppingOrder);       //保存成功重新返回订单的实体
        return shoppingOrder2.getOrderId();
    }
    //按照订单编号查询订单
    public ShoppingOrder findShoppingOrderByOrderNumber(String orderNumber){
       return shoppingOrderRepository.findShoppingOrderByOrderNumber(orderNumber).get(0);
    }
    //修改已有的数据,修改isPayment
    public boolean updateIsPaymentByOrderItemId(int isPayment,int orderItemId){
        orderItemRepository.updateIsPaymentByorderItemId(isPayment,orderItemId);
        //shoppingOrderRepository.updateIsPaymentByOrderNumber(isPayment,orderNumber);
        return true;
    }
    public boolean updateIsPaymentByOrderNumber(int isPayment,String orderNumber){
        shoppingOrderRepository.updateIsPaymentByOrderNumber(isPayment,orderNumber);
        return true;
    }
    //修改已有的数据,修改oTime
    public boolean updateOTimeByOrderNumber(String orderNumber){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String createDate=formatter.format(date);
        shoppingOrderRepository.updateOTimeByOrderNumber(createDate,orderNumber);
        return true;
    }
    //根据用户的id和isPayment，返回用户订单
    public List<ShoppingOrder> findShoppingOrderByUserIdAndIsPayment(int userId,int isPayment){
        // return orderItemRepository.findOrderItemByUserAndIsPayment(userId,isPayment);
        List<ShoppingOrder> shoppingOrders=shoppingOrderRepository.findShoppingOrderByUserAndIsPayment(userId,isPayment);
        List<ShoppingOrder> shoppingOrders1=new LinkedList<>();
        for (ShoppingOrder s:shoppingOrders) {
            for (OrderItem orderItem:s.getOrderItems()){
                if (orderItem.getIsPayment()==isPayment){
                    shoppingOrders1.add(s);
                    break;
                }
            }
        }
        return shoppingOrders1;
    }
    //根据用户的id和isPayment，返回用户订单项
    public List<OrderItem> findOrderItemByUserIdAndIsPayment(int userId,int isPayment){
        // return orderItemRepository.findOrderItemByUserAndIsPayment(userId,isPayment);
        return orderItemRepository.findOrderItemByUserAndIsPayment(userId,isPayment);
    }

    //findShoppingOrderByUserIdAndIsPayment(user.getUserId(),isPayment)
    //根据商家id和isPayment,返回用户订单项
    public List<OrderItem> findOrderItemByBIdAndIsPayment(int bId,int isPayment){
        return orderItemRepository.findAllByBusinessBIdAndIsPayment(bId,isPayment);
    }
    //根据是否支付订单，返回用户的订单
//    public List<ShoppingOrder> findShoppingOrderByIsPayment(int isPayment){
//      //  return shoppingOrderRepository.findShoppingOrderByIsPaymentEquals(isPayment);
//    }
    //后台管理员修改用户订单的收货地址
    public boolean manageUpdateOrderAddress(int orderId,String address,String addressDetail,String zipCode, String consignee,String phone){
        ReceiptsAddress receiptsAddress=new ReceiptsAddress();
        receiptsAddress.setAddressDetail(addressDetail);
        receiptsAddress.setAddress(address);
        receiptsAddress.setConsignee(consignee);
        receiptsAddress.setPhone(phone);
        receiptsAddress.setZipCode(zipCode);
        receiptsAddress.setIsValidation(2);//2为管理员修改
        receiptsAddressRepository.save(receiptsAddress);
        ShoppingOrder shoppingOrder=shoppingOrderRepository.findById(orderId).get();
        shoppingOrder.setReceiptsAddress(receiptsAddress);
        shoppingOrderRepository.save(shoppingOrder);
        return true;
    }
    //返回月销售
    public List<Map<String, Float>> findSaleByMonth(int isPayment, int bId, int year) throws ParseException {
        List<OrderItem> orderItems=orderItemRepository.findOrderItemByIsPaymentAndBusinessBId(isPayment,bId);
        List<Map<String,Float>> saleByMonth1=new LinkedList<>();
        for (int i=1;i<=12;i++){
            Map sale=new HashMap();
            sale.put("month",i);
            sale.put("sales",0.0f);
            saleByMonth1.add(sale);
        }
        for (OrderItem o :orderItems) {
            Map sale=new HashMap();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=sdf.parse(o.getShoppingOrder().getoTime());
            String[] dates=new SimpleDateFormat("yyyy-MM-dd").format(date).toString().split("-");
            System.out.println(date);
            float totalPrice=0;//统计该月的销售额
            if (Integer.parseInt(dates[0])==year) {
                System.out.println(saleByMonth1.get(Integer.parseInt(dates[1]) - 1).values());
                totalPrice=saleByMonth1.get(Integer.parseInt(dates[1]) - 1).get("sales") + (o.getoINumber() * o.getBook().getPrice());
                System.out.println(saleByMonth1.get(Integer.parseInt(dates[1]) - 1).get("month")+":"+totalPrice);
                sale.put("month",Integer.parseInt(dates[1]) - 1);
                sale.put("sales",totalPrice);
                saleByMonth1.set(Integer.parseInt(dates[1]) - 1,sale);
            }
        }
        System.out.println(saleByMonth1);
        return saleByMonth1;
    }
    //后台返回新订单
    public List<ShoppingOrder> findAllNewOrderByBId(int bId){
        List<ShoppingOrder> shoppingOrders=new LinkedList<>();
        //Business business=businessRepository.findById(bId).get();
        List<ShoppingOrder> shoppingOrders1=shoppingOrderRepository.findShoppingOrderByIsPaymentEquals(1);
        //System.out.println("共有几条记录"+shoppingOrders1.size());
        for (ShoppingOrder s : shoppingOrders1) {
            for (OrderItem o:s.getOrderItems()) {
                if (o.getBusiness().getbId()==bId&&o.getIsPayment()==1){
                    shoppingOrders.add(s);//是否有该商家的订单
                break;}
            }
        }
       // System.out.println("这里又有几条记录："+shoppingOrders.size());
        return shoppingOrders;
    }
    //前台，确定已经收货
    public boolean updateOrderItem(int orderItemId) {
        orderItemRepository.updateIsPaymentByorderItemId(3, orderItemId);
        return true;
    }
}
