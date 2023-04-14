<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="菜名" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入菜名"
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
      <el-form-item label="商家" prop="businessName">
        <el-input
          v-model="queryParams.businessName"
          placeholder="请输入商家"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-if="roleid!=='102'"
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:ProductInfo:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-if="roleid!=='102'"
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:ProductInfo:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-if="roleid!=='102'"
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:ProductInfo:remove']"
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
          v-hasPermi="['system:ProductInfo:export']"
        >导出</el-button>
      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ProductInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="菜品序号" align="center" prop="productId" />
      <el-table-column label="菜名" align="center" prop="productName" />
      <el-table-column label="价格" align="center" prop="productPrice" />
      <el-table-column label="详情" align="center" prop="description" />
      <el-table-column label="口味" align="center" prop="productFlavour">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.product_flavour" :value="scope.row.productFlavour"/>
        </template>
      </el-table-column>
      <el-table-column label="商家" align="center" prop="businessName" />
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
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="roleid!=='102'"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:ProductInfo:edit']"
          >修改</el-button>
          <el-button
            v-if="roleid!=='102'"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:ProductInfo:remove']"
          >删除</el-button>
          <el-button
            v-if="roleid!=='103'"
            size="mini"
            type="text"
            icon="el-icon-shopping-cart-2"
            @click="handleShopping(scope.row)"
            v-hasPermi="['system:ProductInfo:remove']"
          >加入购物车</el-button>
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

    <!-- 添加或修改菜品管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="菜名" prop="productName">
          <el-input v-model="form.productName" placeholder="请输入菜名" />
        </el-form-item>
        <el-form-item label="价格" prop="productPrice">
          <el-input v-model="form.productPrice" placeholder="请输入价格" />
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
        <el-form-item label="图片">
          <imageUpload v-model="form.productPicture"/>
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
import { listProductInfo, getProductInfo, delProductInfo, addProductInfo, updateProductInfo, exportProductInfo } from "@/api/system/ProductInfo";

export default {
  name: "ProductInfo",
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
      // 菜品管理表格数据
      ProductInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productName: null,
        productFlavour: null,
        businessId: null,
        businessName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      roleid: '',
    };
  },
  created() {
    this.roleid = localStorage.getItem('roleId')
    this.getList();
  },
  methods: {
    /** 查询菜品管理列表 */
    getList() {
      this.loading = true;
      listProductInfo(this.queryParams).then(response => {
        response.rows.forEach(item => {
          item.productPicture = "/dev-api" + item.productPicture
        });
        this.ProductInfoList = response.rows;
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
        productId: null,
        productName: null,
        productPrice: null,
        description: null,
        productFlavour: null,
        businessId: null,
        businessName: null,
        type: 0,
        status: "0",
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        productPicture: null
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
      this.ids = selection.map(item => item.productId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加菜品管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const productId = row.productId || this.ids
      getProductInfo(productId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改菜品管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.productId != null) {
            updateProductInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProductInfo(this.form).then(response => {
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
      const productIds = row.productId || this.ids;
      this.$modal.confirm('是否确认删除菜品管理编号为"' + productIds + '"的数据项？').then(function() {
        return delProductInfo(productIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 加入购物车按钮操作 */
    handleShopping(row) {
      row.type = 1;
      row.shopNum = 1;
      updateProductInfo(row).then(response => {
        this.$modal.msgSuccess("加入购物车成功");
        this.getList();
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal.confirm('是否确认导出所有菜品管理数据项？').then(() => {
        this.exportLoading = true;
        return exportProductInfo(queryParams);
      }).then(response => {
        this.$download.name(response.msg);
        this.exportLoading = false;
      }).catch(() => {});
    }
  }
};
</script>
