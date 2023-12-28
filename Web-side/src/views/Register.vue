<template>
  <div>
    <el-form ref="loginForm" :model="form" :rules="rules"  label-width="80px" class="login-box">
      <h3 class="login-title">欢迎注册</h3>
      <el-form-item label="用户名" prop="username">
        <el-input type="text" placeholder="请输入账号" v-model="form.username"/>
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input type="text" placeholder="请输入账号" v-model="form.phone"/>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" placeholder="请输入密码" v-model="form.password"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" v-on:click="onSubmit('loginForm')">注册</el-button>
        <el-button v-on:click="close()">取消</el-button>
      </el-form-item>
    </el-form>

    <el-dialog
      title="温馨提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <span>请输入账号和密码</span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import qs from "qs";

export default {
  name: "Register",
  data(){
    return{
      form:{
        username:'',
        phone:'',
        password:''
      },
      rules:{
        username: [
          {required:true,message:'用户名不可为空',trigger:'blur'}
        ],
        phone: [
          {required:true,message:'手机号不可为空',trigger:'blur'}
        ],
        password: [
          {required:true,message:'密码不可为空',trigger:'blur'}
        ]
      },
      dialogVisible:false

    }
  },
  methods: {
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.register();
        } else {
          this.dialogVisible = true;
          return false;
        }
      });
    },
    register(){
      var data = {
        name:this.form.username,
        password:this.form.password,
        phone:this.form.phone
      }
      this.axios.post('/apis/register', qs.stringify(data)).then(successResponse=>{
        console.log(successResponse)
        if(successResponse.data.status == 'success'){
          this.$message({
            type:"success",
            message:"注册成功"
          });
          this.$router.push("/login");
        }else{
          this.$message({
            type:"error",
            message:"注册失败"
          });
        }
      })
    },
    handleClose(){

    },
    close(){
      this.$router.push("/login");
    }
  }
}
</script>

<style scoped>
.login-box {
  border: 1px solid #DCDFE6;
  width: 350px;
  margin: 180px auto;
  padding: 35px 35px 15px 35px;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  box-shadow: 0 0 25px #909399;
}

.login-title {
  text-align: center;
  margin: 0 auto 40px auto;
  color: #303133;
}
</style>
