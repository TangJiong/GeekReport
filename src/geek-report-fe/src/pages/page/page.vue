<template>
  <div class="small-padding-container">
    <el-row :gutter="10">
      <paragraph :span="12" title="气泡图" visual-type="chart"></paragraph>
      <paragraph :span="12" title="柱状图" visual-type="chart"></paragraph>
      <paragraph :span="24" title="统计表" visual-type="table"></paragraph>
      <paragraph :span="24" title="随便什么图" visual-type="chart"></paragraph>
    </el-row>
  </div>
</template>

<script>
import Paragraph from '../paragraph/view'
export default {
  components: {
    Paragraph
  },

  data () {
    return {

    }
  },

  methods: {

  }
}
</script>

<style scoped>

</style>
