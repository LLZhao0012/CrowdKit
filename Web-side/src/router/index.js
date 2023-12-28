import Vue from "vue";
import Router from 'vue-router'

import main from "../views/main";
import Login from "../views/Login";
import UserList from "../views/user/List"
import UserProfile from "../views/user/Profile"
import NotFound from "../views/NotFound";
import CreateTable from "../views/dataModel/CreateTable";
import TableManage from "../views/dataModel/TableManage";
import Register from "../views/Register";
import UpdateTable from "../views/dataModel/UpdateTable";
import AlgorithmSelect from "../views/algorithm/algorithmSelect";
import AlgorithmUpload from "../views/algorithm/algorithmUpload";

Vue.use(Router)

export default new Router({
  mode: "history",
  routes: [
    {
      path: '/main/:name',
      component: main,
      props: true,
      children: [
        {path: '/user/profile/:id', name: 'UserProfile', component: UserProfile, props: true},
        {path: '/user/list', component: UserList},
        {path: '/dataModel/CreateTable/:type', component: CreateTable, props: true},
        {path: '/dataModel/TableManage', component: TableManage},
        {path: '/dataModel/UpdateTable/:tableId', component: UpdateTable},
        {path: '/algorithm/algorithmSelect/', component: AlgorithmSelect},
        {path: '/algorithm/algorithmUpload/', component: AlgorithmUpload},
      ]
    },
    {
      path: '/login',
      component: Login
    },{
      path: '/',
      component: Login
    },{
      path:'/register',
      component: Register
    },{
      path: '/goHome',
      redirect: '/main'
    },{
      path: '*',
      component: NotFound
    }
  ]
})
