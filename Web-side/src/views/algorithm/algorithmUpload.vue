<template>
  <div class="algorithm-upload">
    <div class="upload-container" @dragover.prevent @drop="handleDrop" @click="selectFile">
      <i class="el-icon-upload"></i>
      <p>点击选择文件或拖动文件至此处上传</p>
      <input ref="fileInput" type="file" style="display: none" @change="handleFileChange" />
    </div>

    <div class="input-fields">
      <div class="input-field">
        <label>算法标识:</label>
        <el-input v-model="algorithmId" placeholder="请输入算法标识"></el-input>
      </div>
      <div class="input-field">
        <label>算法类名:</label>
        <el-input v-model="algorithmClassName" placeholder="请输入算法类名"></el-input>
      </div>
      <div class="input-field">
        <label>算法描述:</label>
        <el-input v-model="algorithmDescription" placeholder="请输入算法描述"></el-input>
      </div>
    </div>

    <div class="buttons">
      <el-button type="default" @click="close">关闭</el-button>
      <el-button type="primary" @click="submit">提交</el-button>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import Router from "vue-router";

export default {
  data() {
    return {
      algorithmId: '',
      algorithmClassName: '',
      algorithmDescription: '',
    };
  },
  methods: {
    handleDrop(event) {
      event.preventDefault();
      console.log('File dropped:', event.dataTransfer.files);
    },
    selectFile() {
      this.$refs.fileInput.click();
    },
    handleFileChange(event) {
      console.log('File selected:', event.target.files[0]);
    },
    submit() {
      if (this.validateInputs()) {
        console.log('Upload button clicked');
        console.log('Algorithm ID:', this.algorithmId);
        console.log('Algorithm Class Name:', this.algorithmClassName);
        console.log('Algorithm Description:', this.algorithmDescription);
      }
    },
    close() {
      Vue.use(Router)
    },
    validateInputs() {
      if (!this.algorithmId) {
        this.$message.error('请填写算法标识');
        return false;
      }
      if (!this.algorithmClassName) {
        this.$message.error('请填写算法类名');
        return false;
      }
      if (!this.algorithmDescription) {
        this.$message.error('请填写算法描述');
        return false;
      }
      return true;
    },
  },
};
</script>

<style scoped>
.algorithm-upload {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 20px;
}

.upload-container {
  border: 2px dashed #aaa;
  padding: 20px;
  text-align: center;
  cursor: pointer;
  margin-bottom: 20px;
}

.upload-container i {
  font-size: 40px;
  margin-bottom: 10px;
}

.input-fields {
  display: flex;
  flex-direction: column;
  margin-bottom: 20px;
}

.input-field {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.input-field label {
  margin-right: 10px;
}

.el-input {
  width: 300px;
}

.buttons {
  display: flex;
  justify-content: space-between;
  width: 300px;
}

.el-button {
  width: 140px;
}
</style>
