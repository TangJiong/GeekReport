<template>
  <el-col :span="span">
    <div class="paragraph-container">
      <div class="title-container">
        <div class="paragraph-title">{{ title }}</div>
        <div class="paragraph-menu">
          <el-button-group>
            <el-button size="small"><i class="fa fa-refresh" aria-hidden="true"></i></el-button>
            <el-button size="small" @click="handleEdit"><i class="fa fa-pencil" aria-hidden="true"></i></el-button>
            <el-button size="small"><i class="fa fa-info" aria-hidden="true"></i></el-button>
          </el-button-group>
        </div>
      </div>
      <div class="output-container">
        <!-- <div class="output-container-title">输出结果</div> -->
        <div class="output-wrapper">
          <visualization :type="visualType"></visualization>
        </div>
      </div>
    </div>
  </el-col>
</template>

<script>
import Visualization from '@/components/Visualization'

export default {
  components: {
    Visualization
  },

  props: {
    span: {
      type: Number,
      default: 24
    },
    title: {
      type: String,
      default: '未命名图表'
    },
    visualType: {
      type: String,
      default: 'table'
    }
  },

  data () {
    return {

    }
  },

  methods: {
    handleEdit () {
      this.$router.push({name: 'paragraph-edit', params: {pId: 1}})
    }
  }
}
</script>

<style scoped>
.title-container {
  display: flex;
  flex-wrap: wrap;
  border-bottom: 1px solid #dfe6ec;
  padding: 5px 10px;
  justify-content: space-between;
  align-items: center;
}
.paragraph-container {
  border: 1px solid #dfe6ec;
  margin-bottom: 30px;
}
.paragraph-title {

}
.output-container {
  padding: 5px;
}
.output-wrapper {
  padding: 5px 0;
  color: #999;
  font-size: 14px;
}
</style>
