<template>
  <el-container style="height: 100%; border: 1px solid #eee">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
      <el-menu :default-openeds="[]">
        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-menu"></i>数据模型
          </template>
          <el-menu-item-group>

            <el-menu-item index="1-1">
              <router-link to="/dataModel/TableManage">数据表管理</router-link>
            </el-menu-item>
          </el-menu-item-group>
        </el-submenu>
        <el-submenu index="2">
          <template slot="title">
            <i class="el-icon-s-opportunity"></i>算法管理
          </template>
          <el-menu-item-group>
            <el-menu-item index="2-1">
              <router-link to="/algorithm/algorithmSelect">算法选择</router-link>
            </el-menu-item>
            <el-menu-item index="2-2">
              <router-link to="/algorithm/algorithmUpload">算法上传</router-link>
            </el-menu-item>
          </el-menu-item-group>
        </el-submenu>

      </el-menu>
    </el-aside>

    <el-container style="height: 100%">
      <el-header style="text-align: right; font-size: 12px">
        <el-dropdown>
          <i class="el-icon-setting" style="margin-right: 15px"></i>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>个人信息</el-dropdown-item>
            <el-dropdown-item>退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <span>{{name}}</span>
      </el-header>

      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<style>
body,html,#app,.el-container{
  height: 100%;
}
.el-header {
  background-color: #b3c0d1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
  height: 100%;
}

.el-container.is-vertical {
  overflow: auto;
}

</style>

<script>
export default {
  props: ['name'],
  data() {

  }
};
</script>
