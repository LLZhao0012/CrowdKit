<template>
  <div>
    <el-form ref="loginForm"
             v-loading="loading"
             element-loading-text="登录中"
             :model="form"
             :rules="rules"
             label-width="80px"
             class="login-box">
      <h3 class="login-title">欢迎登录</h3>
      <el-form-item label="账号" prop="username">
        <el-input type="text" placeholder="请输入账号" v-model="form.username"/>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" placeholder="请输入密码" v-model="form.password"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" v-on:click="onSubmit('loginForm')">登录</el-button>
        <el-button v-on:click="Register()">注册</el-button>
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

export default {
  name:"Login",
  data(){
    return{
      loading:'',
      form:{
        username:'',
        password:''
      },
      rules:{
        username: [
          {required:true,message:'账号不可为空',trigger:'blur'}
        ],
        password: [
          {required:true,message:'密码不可为空',trigger:'blur'}
        ]
      },
      dialogVisible:false

    }
  },
  created() {
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
  },
  methods:{
    onSubmit(formName){
      this.$refs[formName].validate((valid)=>{
        if(valid){
          this.login()
        }else {
          this.dialogVisible=true;
          return false;
        }
      });
    },

    login() {
      this.$router.push("/main/" + this.form.username);
    },

    Register(){
      this.$router.push("/register")
    },
    handleClose(){

    }
  }
}
</script>
<style lang="scss" scoped>
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

