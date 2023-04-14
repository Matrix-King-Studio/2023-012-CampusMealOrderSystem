<template>
  <div class="app-container">
    <!-- 公告 -->
    <div>
      <div style="font-size: -webkit-xxx-large;font-size: xxx-large;color: burlywood;font-family: cursive;padding: 30px;">公告信息</div>
      <div class="boxShow">
        <div style="color: gray;font-size: xx-large;font-family: cursive;">{{ noticeList[0].noticeTitle }}</div>
        <div style="font-size: x-large;margin-bottom: 15px;" v-html="noticeList[0].noticeContent"></div>
      </div>
    </div>
    <!-- <div style="margin:30px 50px;font-size: xxx-large;">
      <p> {{ reUser===null?auser:reUser }} </p>
      <p>欢迎您的登录！</p>
    </div> -->

    <div>
        <div style="font-size: -webkit-xxx-large;font-size: xxx-large;color: burlywood;font-family: cursive;padding: 30px;">订单统计信息</div>
        <div class="boxShow">
          <div v-for="(value,key) in orderData" :key="key">
            <div style="font-size: x-large;margin-bottom: 15px;" v-html="value.name +'：'+ value.value"></div>
          </div>
        </div>
      </div>
  </div>
</template>

<script>
import { listNotice } from "@/api/system/notice";
import { listOrderInfo, getOrderInfo, delOrderInfo, addOrderInfo, updateOrderInfo, exportOrderInfo } from "@/api/system/OrderInfo";

export default {
  data(){
    return{
      auser: '',
      reUser: '',
      noticeList: [],
      orderData: []
    }
  },
  mounted(){
    this.auser = localStorage.getItem('auser')
    this.reUser = localStorage.getItem('reUser')
    this.getList();
    this.orderStatistic();
  },
  methods:{
    /** 查询公告列表 */
    getList() {
      listNotice(this.queryParams).then(response => {
        this.noticeList = response.rows;
      });
    },
    //订单统计
    orderStatistic() {
        listOrderInfo({
          pageNum: 1,
          pageSize: 10,
          type: 2
        }).then(response => {
          let data = response.rows[0];
          this.orderData = [];
          this.orderData.push({
          value: data.todayOrder,
          name: '今日新增'
          },
          {
            value: data.allOrder,
            name: '订单总量'
          })
        });
    }
  }
}
</script>
<style scoped>
.boxShow{
  border: 1px solid #e5dede;
  padding: 30px;
  margin: 25px;
  box-shadow: blue 10px;
  width: 70%;
  box-shadow: 10px 10px 5px #e1ddcf;
}
</style>
