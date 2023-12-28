<template>
  <div class="algorithm-selection">
    <div class="radio-buttons">
      <el-radio-group v-model="selectedAlgorithm">
        <el-radio label="Random">Random</el-radio>
        <el-radio label="NNP">NNP</el-radio>
        <el-radio label="RTP">RTP</el-radio>
        <el-radio label="GA">GA</el-radio>
        <el-radio label="MT-GrdPT">MT-GrdPT</el-radio>
      </el-radio-group>
    </div>

    <div class="algorithm-table">
      <el-table :data="algorithmData" style="width: 100%" border>
        <el-table-column prop="id" label="Algorithm ID"></el-table-column>
        <el-table-column prop="description" label="Algorithm Description"></el-table-column>
      </el-table>
    </div>

    <div class="buttons">
      <el-button type="default" @click="close">关闭</el-button>
      <el-button type="primary" @click="submit">提交</el-button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      selectedAlgorithm: '',
      algorithmData: [
        { id: 'Random', description: 'Random allocation algorithm' },
        { id: 'NNP', description: 'Nearest neighbor priority allocation algorithm' },
        { id: 'RTP', description: 'Nearest online time priority allocation algorithm' },
        { id: 'GA', description: 'Genetic algorithm' },
        { id: 'MT-GrdPT', description: 'User-centric task allocation algorithm under user resource scarcity' }
      ]
    };
  },
  methods: {
    close() {
      console.log('Close button clicked');
    },
    submit() {
      console.log('Submit button clicked');
    }
  }
};
</script>

<style scoped>
.algorithm-selection {
  padding: 20px;
}

.radio-buttons {
  margin-bottom: 20px;
}

.algorithm-table {
  margin-bottom: 20px;
}

.el-table th {
  background-color: #ccc;
}

.buttons {
  margin-top: 20px;
}
</style>
