
<template xmlns="http://www.w3.org/1999/html">
  <div class="tableDate">
    <div class="demo-input-size">
      <el-input
        placeholder="请输入表名称"
        v-model="tableName"
        style="width: 25%" >
      </el-input>
      <el-input
        placeholder="请输入表描述"
        v-model="tableDescription"
        style="width: 60%" >
      </el-input>
    </div>
    <br>
    <div class="button">
      <el-button class="el-icon-plus" @click.prevent="addRow()">新增</el-button>
      <el-button class="el-icon-minus" @click.prevent="delData()">删除</el-button>
    </div>
    <br>
    <div class="table">
      <el-table
        :data="tableData"
        ref="table"
        tooltip-effect="dark"
        border
        stripe
        style="width: 93%"
        @selection-change='selectRow'>
        <el-table-column type="selection" width="45" align="center"></el-table-column>
        <el-table-column label="序号"  type="index" width="60" align="center"></el-table-column>
        <el-table-column  label="字段名称" align="center">
          <template slot-scope="scope">
            <el-input class="require_des" v-model="scope.row.name" :disabled="!scope.row.canEdit"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="字段类型" align="center">
          <template slot-scope="scope">
            <el-select v-model="scope.row.type" placeholder="请选择" :disabled="!scope.row.canEdit">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="长度" align="center">
          <template slot-scope="scope">
            <el-input class="require_des" v-model="scope.row.length"  :disabled="scope.row.canEdit == false || (scope.row.type !='char' && scope.row.type !='varchar')? true : false" ></el-input>
          </template>
        </el-table-column>
        <el-table-column label="字段描述" align="center">
          <template slot-scope="scope">
            <el-input type="textarea" class="remark" v-model="scope.row.description" :disabled="!scope.row.canEdit"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="主键" align="center">
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.keyValue" @change="judgeKey" :disabled="scope.row.canEdit == false || scope.row.allowNull== true || (hasKey == true && keyRowNum != scope.$index)? true: false"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="允许空值" align="center">
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.allowNull" :disabled="scope.row.canEdit == false || scope.row.keyValue== true ? true: false"></el-checkbox>
          </template>
        </el-table-column>

      </el-table>
    </div>
    <br>
    <div>
      <el-row>
        <el-button  @click="close">关闭</el-button>
        <el-button type="primary" @click="commit">提交</el-button>
      </el-row>
    </div>
  </div>
</template>
<script>


import qs from "qs";

export default {
  props:['type'],
  data () {
    return {
      tableData: [],
      selectlistRow: [],
      rowNum:1,
      options: [{
        value: 'datetime',
        label: 'datetime'
      },{
        value: 'date',
        label: 'date'
      },{
        value: 'decimal',
        label: 'decimal'
      }, {
        value: 'varchar',
        label: 'varchar'
      },{
        value: 'char',
        label: 'char'
      },{
        value: 'text',
        label: 'text'
      }, {
        value: 'int',
        label: 'int'
      }, {
        value: 'boolean',
        label: 'boolean'
      }, {
        value: 'double',
        label: 'double'
      },{
        value: 'float',
        label: 'float'
      }],
      value: '',
      tableName:'',
      tableDescription:'',
      hasKey:'',
      keyRowNum:'',
      tableType:this.type
    }
  },
  mounted() {
    console.log("表类型:" + this.tableType);
    this.setTableType();
  },
  methods: {
    // 获取表格选中时的数据
    selectRow (val) {
      this.selectlistRow = val
    },
    // 增加行
    addRow () {
      var list = {
        rowNum:this.rowNum,
        post_id:[],
        name: '',
        type:'',
        length:'',
        description:'',
        keyValue:'',
        allowNull:'',
        canEdit: true
      };
      this.tableData.push(list);
      this.rowNum += 1;
    },
    delData () {
      console.log("删除前");
      console.log(this.tableData);
      for (let i = 0; i < this.selectlistRow.length; i++) {
        let val = this.selectlistRow[i];
          if(val.canEdit == false){
            window.alert("默认字段不能删除");
            continue;
          }
          this.tableData.forEach((v, i) => {
            if (val.rowNum === v.rowNum) {
              // i 为选中的索引
              this.tableData.splice(i, 1)
            }
          })
        // })
      }
      this.$refs.table.clearSelection()
      console.log("删除后");
      console.log(this.tableData);
      this.judgeKey();
    },
    setTaskType(){
      this.tableData=[{
        rowNum: 1,
        name: 'id',
        type: 'int',
        length: '',
        description: 'Identifier of the task',
        keyValue: true,
        allowNull: false,
        canEdit: false
      },{
        rowNum: 2,
        name: 'description',
        type: 'text',
        length: '',
        description: 'Description of the task',
        keyValue: false,
        allowNull: false,
        canEdit: false
      },{
        rowNum: 3,
        name: 'start_time',
        type: 'datetime',
        length: '',
        description: 'Start time of the task',
        keyValue: false,
        allowNull: false,
        canEdit: false
      },{
        rowNum: 4,
        name: 'end_time',
        type: 'datetime',
        length: '',
        description: 'End time of the task',
        keyValue: false,
        allowNull: false,
        canEdit: false
      },{
        rowNum: 5,
        name: 'longitude',
        type: 'double',
        length: '',
        description: 'Longitude of the task',
        keyValue: false,
        allowNull: false,
        canEdit: false
      },{
        rowNum: 6,
        name: 'latitude',
        type: 'double',
        length: '',
        description: 'Latitude of the task',
        keyValue: false,
        allowNull: false,
        canEdit: false
      },{
        rowNum: 7,
        name: 'worker_number',
        type: 'int',
        length: '',
        description: 'Number of workers required for the task',
        keyValue: false,
        allowNull: false,
        canEdit: false
      }];
      this.rowNum = 8;
      this.hasKey = true;
      this.keyRowNum = 1;
    },
    setWorkerType(){
      this.tableData=[{
        rowNum: 1,
        name: 'id',
        type: 'int',
        length: '',
        description: '工人id',
        keyValue: true,
        allowNull: false,
        canEdit: false
      },{
        rowNum: 2,
        name: 'longitude',
        type: 'double',
        length: '',
        description: '最近一次上线时工人所在位置的经度坐标',
        keyValue: false,
        allowNull: false,
        canEdit: false
      },{
        rowNum: 3,
        name: 'latitude',
        type: 'double',
        length: '',
        description: '最近一次上线时工人所在位置的纬度坐标',
        keyValue: false,
        allowNull: false,
        canEdit: false
      },{
        rowNum: 4,
        name: 'last_online_time',
        type: 'datetime',
        length: '',
        description: '工人最近一次上线时间',
        keyValue: true,
        allowNull: false,
        canEdit: false
      }];
      this.rowNum = 5;
      this.keyRowNum = 1;
      this.hasKey = true;
    },
    setTableType(){
      if(this.tableType == 'Task'){
        this.setTaskType();
      }else if(this.tableType == 'Worker'){
        this.setWorkerType();
      }
    },
    check(){
      if(this.tableName == ''){
        this.$message({
          type:"warning",
          message:"数据表名称不能为空"
        })
        return false;
      }
      if(this.tableData.length == 0){
        this.$message({
          type:"warning",
          message:"请添加数据列"
        })
        return false;
      }
      for(var i = 0; i < this.tableData.length; i++){
        if(this.tableData[i].name == '' || this.tableData[i].type == ''){
          this.$message({
            type:"warning",
            message:"数据列的字段名称和字段类型不能为空"
          })
          return false;
        }
        if(this.tableData[i].type == 'char'){
          if(this.tableData[i].length == '' || this.tableData[i].length > 255){
            this.$message({
              type:"warning",
              message:"长度不合法（char的长度不能为空且最大为255）"
            })
            return false;
          }
        }
        if(this.tableData[i].type == 'varchar'){
          if(this.tableData[i].length == '' || this.tableData[i].length > 65535){
            this.$message({
              type:"warning",
              message:"长度不合法（varchar的长度不能为空且最大为65535）"
            })
            return false;
          }
        }
      }
      return true;
    },
    commit(){
      if(this.check() == false){
        return;
      }
      console.log(this.tableData);
      var sql = 'create table ' + this.tableName + '(';
      for(var i = 0; i < this.tableData.length; i++){
        var cName = this.tableData[i].name;
        var type = this.tableData[i].type;
        if(type == 'char' || type == 'varchar'){
          type += "(" + this.tableData[i].length + ")";
        }
        var keyOrNull = '';
        if(this.tableData[i].keyValue == true){
          keyOrNull = 'PRIMARY KEY';
        }else if(this.tableData[i].allowNull == false){
          keyOrNull = 'Not null';
        }
        var com = '';
        if(this.tableData[i].description != ''){
          com = 'Comment "' + this.tableData[i].description + '"';
        }
        var col = cName + ' ' + type + ' ' + keyOrNull + ' ' + com;
        if(i == this.tableData.length - 1){
          sql += col;
        }else{
          sql += col + ',';
        }
      }
      if(this.tableDescription != ''){
        sql += ')Comment="';
        sql += this.tableDescription;
        sql += '";';
      }else{
        sql += ');';
      }
      console.log(sql);
      var data = {
        sql:sql,
        type:this.tableType
      };
      this.axios.post('/apis/createTable', qs.stringify(data)).then(successResponse=>{
        console.log(successResponse);
        if(successResponse.data.status == 'success'){
          this.$message({
            type:"success",
            message:"创建成功"
          })
          this.close();
        }else if(successResponse.data.data.errCode == "20003"){

          this.$router.push('/login');
        } else{
          this.$message({
            type:"error",
            message:successResponse.data.data
          })
        }
      });
    },
    close(){
      this.$router.push('/dataModel/TableManage');
    },
    judgeKey(){
      this.hasKey = false;
      for(var i = 0; i < this.tableData.length; i++){
        if(this.tableData[i].keyValue == true){
          this.hasKey = true;
          this.keyRowNum = i;
        }
      }
    }
  }
}
</script>



