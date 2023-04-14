<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="产品名" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入产品名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="购买人" prop="buyerName">
        <el-input
          v-model="queryParams.buyerName"
          placeholder="请输入购买人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商家" prop="businessName">
        <el-input
          v-model="queryParams.businessName"
          placeholder="请输入商家"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="口味" prop="productFlavour">
        <el-select v-model="queryParams.productFlavour" placeholder="请选择口味" clearable size="small">
          <el-option
            v-for="dict in dict.type.product_flavour"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="订单状态" prop="orderStatus">
        <el-select v-model="queryParams.orderStatus" placeholder="请选择订单状态" clearable size="small">
          <el-option
            v-for="dict in dict.type.order_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
<!--      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:OrderInfo:add']"
        >新增</el-button>
      </el-col>-->
<!--      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:OrderInfo:edit']"
        >修改</el-button>
      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:OrderInfo:remove']"
        >删除</el-button>
      </el-col>
<!--      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['system:OrderInfo:export']"
        >导出</el-button>
      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-pie-chart"
          size="mini"
          @click="handleChart"
          v-hasPermi="['system:OrderInfo:add']"
        >{{ this.chartButtonText }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="OrderInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单号" align="center" prop="orderId" />
      <el-table-column label="产品名" align="center" prop="productName" />
      <el-table-column label="价格" align="center" prop="productPrice" />
      <el-table-column label="图片" align="center" prop="productPicture">
        <template slot-scope="scope">
          <el-image
            style="width: 100px; height: 100px"
            :src="scope.row.productPicture"
            :preview-src-list="[scope.row.productPicture]"
          >
          </el-image>
        </template>
      </el-table-column>
      <el-table-column label="详情" align="center" prop="description" />
      <el-table-column label="购买人" align="center" prop="buyerName" />
      <el-table-column label="商家" align="center" prop="businessName" />
      <el-table-column label="口味" align="center" prop="productFlavour">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.product_flavour" :value="scope.row.productFlavour"/>
        </template>
      </el-table-column>
      <el-table-column label="数量" align="center" prop="shopNum" />
      <el-table-column label="订单状态" align="center" prop="orderStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_status" :value="scope.row.orderStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="roleid=='1'"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:OrderInfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:OrderInfo:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-truck"
            v-if="scope.row.orderStatus == 1 && roleid!=='102'"
            @click="handleDelivery(scope.row)"
            v-hasPermi="['system:OrderInfo:edit']"
          >配送</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-finished"
            v-if="scope.row.orderStatus == 2 && roleid!=='103'"
            @click="handleConfirm(scope.row)"
            v-hasPermi="['system:OrderInfo:edit']"
          >确认收货</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <div v-if="isShowChart" id="main" style="width:700px;height:600px;margin:20px auto;"></div>

    <!-- 添加或修改订单管理 对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="产品名" prop="productName">
          <el-input v-model="form.productName" placeholder="请输入产品名" />
        </el-form-item>
        <el-form-item label="价格" prop="productPrice">
          <el-input v-model="form.productPrice" placeholder="请输入价格" />
        </el-form-item>
        <el-form-item label="图片">
          <imageUpload v-model="form.productPicture"/>
        </el-form-item>
        <el-form-item label="详情" prop="description">
          <el-input v-model="form.description" placeholder="请输入详情" />
        </el-form-item>
        <el-form-item label="购买人ID" prop="buyerId">
          <el-input v-model="form.buyerId" placeholder="请输入购买人ID" />
        </el-form-item>
        <el-form-item label="购买人" prop="buyerName">
          <el-input v-model="form.buyerName" placeholder="请输入购买人" />
        </el-form-item>
        <el-form-item label="商家" prop="businessName">
          <el-input v-model="form.businessName" placeholder="请输入商家" />
        </el-form-item>
        <el-form-item label="口味" prop="productFlavour">
          <el-select v-model="form.productFlavour" placeholder="请选择口味">
            <el-option
              v-for="dict in dict.type.product_flavour"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="订单状态" prop="orderStatus">
          <el-select v-model="form.orderStatus" placeholder="请选择订单状态">
            <el-option
              v-for="dict in dict.type.order_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listOrderInfo, getOrderInfo, delOrderInfo, addOrderInfo, updateOrderInfo, exportOrderInfo } from "@/api/system/OrderInfo";
import * as echarts from 'echarts';

export default {
  name: "OrderInfo",
  dicts: ['product_flavour', 'order_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 订单管理 表格数据
      OrderInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productName: null,
        buyerId: null,
        buyerName: null,
        businessId: null,
        businessName: null,
        productFlavour: null,
        orderStatus: null,
        type: 0
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      roleid: '',
      chartButtonText: '展示报表',
      isShowChart: false,
      chartData: []
    };
  },
  created() {
    this.roleid = localStorage.getItem('roleId')
    this.getList();
  },
  methods: {
    /** 查询订单管理 列表 */
    getList() {
      this.loading = true;
      listOrderInfo(this.queryParams).then(response => {
        this.OrderInfoList = response.rows;
        this.total = response.total;
        if (this.isShowChart) {
          this.chartData = [];
          listOrderInfo({
            pageNum: 1,
            pageSize: 10,
            type: 1
          }).then(response => {
            let data = response.rows;
            data.forEach(item => {
              let tagName = '待配送';
              if (item.orderStatus == 1) {
                tagName = '待配送';
              } else if (item.orderStatus == 2) {
                tagName = '配送中';
              } else if (item.orderStatus == 3) {
                tagName = '配送完成';
              }
              this.chartData.push({
                value: item.orderCount,
                name: tagName
              })
            });
            this.chart();
          });
        }
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        orderId: null,
        productId: null,
        productName: null,
        productPrice: null,
        productPicture: null,
        description: null,
        buyerId: null,
        buyerName: null,
        type: null,
        status: "0",
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        businessId: null,
        businessName: null,
        productFlavour: null,
        orderStatus: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.orderId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加订单管理 ";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const orderId = row.orderId || this.ids
      getOrderInfo(orderId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改订单管理 ";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.orderId != null) {
            updateOrderInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrderInfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const orderIds = row.orderId || this.ids;
      this.$modal.confirm('是否确认删除订单管理 编号为"' + orderIds + '"的数据项？').then(function() {
        return delOrderInfo(orderIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 配送按钮操作 */
    handleDelivery(row) {
      row.orderStatus = 2;
      updateOrderInfo(row).then(response => {
        this.$modal.msgSuccess("配送成功");
        this.getList();
      });
    },
    /** 确认收货按钮操作 */
    handleConfirm(row) {
      row.orderStatus = 3;
      updateOrderInfo(row).then(response => {
        this.$modal.msgSuccess("确认收货成功");
        this.getList();
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal.confirm('是否确认导出所有订单管理 数据项？').then(() => {
        this.exportLoading = true;
        return exportOrderInfo(queryParams);
      }).then(response => {
        this.$download.name(response.msg);
        this.exportLoading = false;
      }).catch(() => {});
    },
    // 报表按钮操作
    handleChart(){
      if(this.isShowChart){
        this.isShowChart = false;
        this.chartButtonText = '展示报表';
      }else{
        this.loading = true;
        this.isShowChart = true;
        this.chartData = [];
        listOrderInfo({
          pageNum: 1,
          pageSize: 10,
          type: 1
        }).then(response => {
          let data = response.rows;
          data.forEach(item => {
            let tagName = '待配送';
            if(item.orderStatus == 1){
              tagName = '待配送';
            }else if (item.orderStatus == 2) {
              tagName = '配送中';
            } else if (item.orderStatus == 3) {
              tagName = '配送完成';
            }
            this.chartData.push({
              value: item.orderCount,
              name: tagName
            })
          });
          this.chart();
          this.chartButtonText = '隐藏报表';
          this.loading = false;
        });
      }
    },
    // 饼状图 订单
    chart() {
      var chartDom = document.getElementById('main');
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        title: {
          text: '订单报表',
          subtext: '实时数据',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '订单',
            type: 'pie',
            radius: '50%',
            data: this.chartData,
              // [
              //   { value: 1048, name: 'Search Engine' },
              //   { value: 735, name: 'Direct' },
              //   { value: 580, name: 'Email' },
              //   { value: 484, name: 'Union Ads' },
              //   { value: 300, name: 'Video Ads' }
              // ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };

      option && myChart.setOption(option);
    },
  }
};
</script>
