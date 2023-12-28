<template>
  <div>
    <el-tabs v-model="activeName" >
      <el-tab-pane label="数据模型管理" name="first">
        <div class="button">
          <el-button type="primary" class="el-icon-plus" @click.prevent="addRow()">新增</el-button>
          <el-button type="success" icon="el-icon-share" @click="multiGenCode()">批量生成</el-button>
          <el-button type="danger" class="el-icon-delete" @click="delRow()">批量删除</el-button>
        </div>
        <br>
        <div class="table">
          <el-table
            v-loading="loading"
            :data="tableList"
            ref="table"
            tooltip-effect="dark"
            border
            stripe
            style="width: 95%"
            @selection-change='selectRow'>
            <el-table-column type="selection" width="50" align="center"></el-table-column>
            <el-table-column label="序号" type="index" width="60" align="center"></el-table-column>
            <el-table-column prop="tableName" label="表名称" min-width="20%" align="center">
            </el-table-column>
            <el-table-column prop="tableComment" label="表描述" min-width="25%" align="center">

            </el-table-column>
            <el-table-column label="操作" min-width="30%" align="center">
              <template slot-scope="scope">
                <el-button-group >
                  <el-button type="info" icon="el-icon-edit" @click="testlist()">编辑</el-button>

                  <el-button type="danger" icon="el-icon-delete" @click.native.prevent="delARowPre(scope.$index)">删除</el-button>
                  <el-button type="success" icon="el-icon-share" @click.native.prevent="genCode(scope.$index)">生成代码</el-button>
                </el-button-group>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-tab-pane>
    </el-tabs>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[5,10,20,30,40]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="totalSize">
    </el-pagination>
    <el-dialog
      title="请选择要创建的表类型"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <el-select v-model="tableType" placeholder="请选择表类型" >
        <el-option
          v-for="item in tableTypeOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="createTable">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import ps from "qs";

export default {
  name: "TableManage",
  data(){
    return{
      loading:'',
      activeName: 'first',
      tableList:[],
      selectListRow:[],
      rowNum:'',
      tableType:'others',
      tableTypeOptions:[{
        value:'Task',
        label:'Task(继承MetaTask)'
      },{
        value:'Worker',
        label:'Worker(继承MetaWorker)'
      },{
        value: 'others',
        label: '其他'
      }],
      dialogVisible:false,
      totalSize:0,
      currentPage:1,
      pageSize:5,
    }
  },
  created() {
    // alert("页面加载")
    this.axios.interceptors.request.use((config)=>{
      this.loading = true
      return config;
    }, function (error) {
      return Promise.reject(error);
    });

    this.axios.interceptors.response.use((response)=> {
      this.loading = false
      return response;
    }, function (error) {
      return Promise.reject(error);
    });
    this.init();
  },
  methods:{
    init(){
      let data = {
        currentPage:this.currentPage,
        pageSize:this.pageSize
      }
      this.axios.post('/apis/getTableByPage',ps.stringify(data)
      ).then(successResponse=>{
        console.log(successResponse)
        this.tableList = successResponse.data.data.pageInfo.list;
        this.totalSize = successResponse.data.data.totalSize;
      });
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.currentPage = 1;
      this.init();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val;
      this.init();
    },
    selectRow (val) {
      this.selectListRow = val
    },
    addRow () {
      this.dialogVisible=true;
    },
    createTable(){
      console.log("tableTYpe" + this.tableType);
      this.$router.push("CreateTable/" + this.tableType);
    },
    handleClose(){
      this.dialogVisible = false;
    },

    delRow() {
      if(this.selectListRow.length == 0){
        this.$message({
          type:"info",
          message:"请选择要删除的数据表"
        })
        return;
      }
      this.$confirm('此操作将永久删除该数据表, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.delTable();
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },

    delTable(){
      let data = [];
      for(let i = 0; i < this.selectListRow.length; i++){
        let val = this.selectListRow[i];
        this.tableList.forEach((v, i) => {
          if (val.tableName === v.tableName) {
            data.push(v);
            // i 为选中的索引
            this.tableList.splice(i, 1);
          }
        })
      }
      this.doDel(data);
    },
    delARowPre(index){
      this.$confirm('此操作将永久删除该数据表, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.delARow(index);
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },

    delARow(index){
      console.log("删除" + this.tableList[index].tableName);
      let data = [];
      data.push(this.tableList[index]);
      this.tableList.splice(index,1);
      this.doDel(data);
    },

    doDel(data){
      this.axios.post('/apis/deleteTable',data).then(successResponse=>{
        console.log(successResponse.data);
        if(successResponse.data.status == 'success'){
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
          this.init();
        }else{
          this.$message({
            type: 'error',
            message: '删除失败!'
          });
        }
      });
    },
    genCode(index){
      console.log("生成代码" + this.tableList[index].tableName);
      var data = {
        name:this.tableList[index].tableName
      };
      this.axios.post(
        '/apis/genCode',
        ps.stringify(data),
        {
          responseType:'blob'
        }
      ).then(response=>{
        console.log(response);
        const blob = response.data;
        const reader = new FileReader();
        reader.readAsDataURL(blob);
        reader.onload = (e) => {
          const a = document.createElement('a');
          console.log(response.headers["content-disposition"])
          a.download = response.headers["content-disposition"].split('filename=')[1];
          console.log(a.download);
          a.href = e.target.result;
          document.body.appendChild(a);
          a.click();
          document.body.removeChild(a);
        }
      })
    },

    multiGenCode(){
      if(this.selectListRow.length == 0){
        this.$message({
          type:"info",
          message:"请选择要生成代码的数据表"
        })
        return;
      }
      var data = [];
      for(let i = 0; i < this.selectListRow.length; i++){
        let val = this.selectListRow[i];
        this.tableList.forEach((v, i) => {
          if (val.tableName === v.tableName) {
            data.push(v);
          }
        })
      }
      this.$refs.table.clearSelection();
      this.axios.post(
        '/apis/multiGenCode',
        data,
        {
          responseType:'blob'
        }
      ).then(response=>{
        console.log(response);
        const blob = response.data;
        const reader = new FileReader();
        reader.readAsDataURL(blob);
        reader.onload = (e) => {
          const a = document.createElement('a');
          console.log(response.headers["content-disposition"])
          a.download = response.headers["content-disposition"].split('filename=')[1];
          console.log(a.download);
          a.href = e.target.result;
          document.body.appendChild(a);
          a.click();
          document.body.removeChild(a);
        }
      })
    },
    testlist(){
      var data = {
        IdS:[1,2,3]
      };
      this.axios.post("/apis/test",data).then(res=>{
        console.log(res.data)
      })
    }
  }
}
</script>

<style scoped>

</style>
