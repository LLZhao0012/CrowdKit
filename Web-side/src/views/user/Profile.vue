<template>

  <div>
    <h1>个人信息</h1>
    {{id}}
  </div>

</template>

<script>
export default {
  name: "UserProfile",
  props: ['id'],
  beforeRouteEnter:(to, from, next)=>{
    console.log("进入路由之前")
    next(vm => {
      vm.getData()
    })
  },
  beforeRouteLeave:(to, from, next)=>{
    console.log("进入路由之后")
    next()
  },
  methods: {
    getData: function () {
      this.axios({
        method: "get",
        url: 'http://localhost:8080/static/mock/data.json'
      }).then(function (response){
        console.log(response)
      })
    }
  }
}
</script>

<style scoped>

</style>
