<template>

</template>

<script>
export default {
  name: "UpdateTable",
  props:['tableId'],
  data(){

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
  }
}
</script>

<style scoped>

</style>
