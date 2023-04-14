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
          v-hasPermi="['system:ShopCart:add']"
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
          v-hasPermi="['system:ShopCart:edit']"
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
          v-hasPermi="['system:ShopCart:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-s-claim"
          size="mini"
          :disabled="multiple"
          @click="handlePay"
          v-hasPermi="['system:ShopCart:add']"
        >结算</el-button>
      </el-col>
<!--      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['system:ShopCart:export']"
        >导出</el-button>
      </el-col>-->
      <el-tag v-if="isShowPay">总价：{{ this.totalMoney }}￥</el-tag>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ShopCartList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
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
      <el-table-column label="数量" align="center" prop="shopNum"  width="200px">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.shopNum" @change="value => handleNumChange(scope.row,value)" :min="1" size="mini"></el-input-number>
          </template>
        </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
<!--          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:ShopCart:edit']"
          >修改</el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:ShopCart:remove']"
          >删除</el-button>
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

    <!-- 添加或修改购物车对话框 -->
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listShopCart, getShopCart, delShopCart, addShopCart, updateShopCart, exportShopCart } from "@/api/system/ShopCart";

export default {
  name: "ShopCart",
  dicts: ['product_flavour'],
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
      // 购物车表格数据
      ShopCartList: [],
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
        productFlavour: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      totalMoney: 0,
      isShowPay: false,
      selection: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询购物车列表 */
    getList() {
      this.loading = true;
      listShopCart(this.queryParams).then(response => {
        this.ShopCartList = response.rows;
        this.total = response.total;
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
        shopCartId: null,
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
        productFlavour: null
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
      this.selection = selection;
      if(selection.length > 0){
        this.isShowPay = true;
        this.totalMoney = 0;
        selection.forEach(item => {
          this.totalMoney += item.productPrice * item.shopNum;
        });
      }else{
        this.isShowPay = false;
        this.totalMoney = 0;
      }
      this.ids = selection.map(item => item.shopCartId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加购物车";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const shopCartId = row.shopCartId || this.ids
      getShopCart(shopCartId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改购物车";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.shopCartId != null) {
            updateShopCart(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addShopCart(this.form).then(response => {
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
      const shopCartIds = row.shopCartId || this.ids;
      this.$modal.confirm('是否确认删除购物车编号为"' + shopCartIds + '"的数据项？').then(function() {
        return delShopCart(shopCartIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 结算按钮操作 */
    handlePay() {
      this.loading = true;
      let count = this.selection.length;
      let index = 0;
      this.selection.forEach(item => {
        item.type = 1;
        updateShopCart(item).then(response => {
          index += 1;
          if(index == count){
            this.getList();
            this.$modal.msgSuccess("结算成功");
          }
        }).catch(() => {
          index += 1;
          if(index == count ){
            this.loading = false;
          }
         });
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal.confirm('是否确认导出所有购物车数据项？').then(() => {
        this.exportLoading = true;
        return exportShopCart(queryParams);
      }).then(response => {
        this.$download.name(response.msg);
        this.exportLoading = false;
      }).catch(() => {});
    },
    handleNumChange(row,value){
      this.loading = true;
      row.shopNum = value;
      row.type = 0;
      updateShopCart(row).then(response => {
        this.$modal.msgSuccess("产品数量修改成功");
        this.getList();
      });
    }
  }
};
</script>
