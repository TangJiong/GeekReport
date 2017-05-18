<template>
  <div class="paragraph-wrapper" :style="sizeStyle">
    <div class="title-container">
      <div class="paragraph-title">{{ paragraph.title }}</div>
      <div class="paragraph-menu">
        <el-button-group>
          <el-button size="small"><i class="fa fa-refresh" aria-hidden="true"></i></el-button>
          <el-button v-if="editable" size="small" @click="handleEdit"><i class="fa fa-pencil" aria-hidden="true"></i></el-button>
          <el-button size="small"><i class="fa fa-info" aria-hidden="true"></i></el-button>
        </el-button-group>
      </div>
    </div>
    <div class="output-container">
      <!-- <div class="output-container-title">输出结果</div> -->
      <div class="output-wrapper">
        <visualization :type="paragraph.default_visual_id"></visualization>
      </div>
    </div>
  </div>
</template>

<script>
import Visualization from '@/components/Visualization'

export default {
  components: {
    Visualization
  },

  props: {
    paragraph: {
      type: Object,
      required: true,
      default: () => {
        return {
          id: 0,
          title: '',
          width: 24,
          height: 1,
          default_visual_id: -1
        }
      }
    },
    editable: {
      type: Boolean,
      default: false
    }
  },

  data () {
    return {

    }
  },

  computed: {
    sizeStyle () {
      let base = 4.1
      let width = this.paragraph.width
      let realWidth = (width === 24 ? 100 : width * base)
      return {
        flex: '0 0 ' + realWidth + '%'
      }
    }
  },

  methods: {
    handleEdit () {
      this.$router.push({name: 'paragraph-edit', params: {pId: this.paragraph.id}})
    }
  }
}
</script>

<style scoped>
.paragraph-wrapper {
  border: 1px solid #dfe6ec;
  margin: 10px 0;
}
.title-container {
  display: flex;
  flex-wrap: wrap;
  border-bottom: 1px solid #dfe6ec;
  padding: 5px 10px;
  justify-content: space-between;
  align-items: center;
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
