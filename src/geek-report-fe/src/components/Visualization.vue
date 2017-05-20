<template>
  <div class="visual-wrapper">
    <visual-table v-if="config.chart_type === 'table'" :data="data"></visual-table>
    <visual-chart v-else :config="chartConfig"></visual-chart>
  </div>
</template>

<script>
import VisualChart from './VisualChart'
import VisualTable from './VisualTable'
import {
  VisualizationService
} from '@/services'

export default {
  components: {
    VisualChart,
    VisualTable
  },

  props: {
    data: {
      type: Object,
      required: true,
      default: () => {
        return {
          rows: [],
          columns: []
        }
      }
    },
    config: {
      type: Object,
      required: true,
      default: () => {
        return {
          chart_type: 'table'
        }
      }
    }
  },

  data () {
    return {

    }
  },

  computed: {
    chartConfig () {
      return VisualizationService.build(this.data.rows, this.config)
    }
  },

  mounted () {

  },

  methods: {

  }
}
</script>

<style scoped>
.visual-wrapper {
  height: 360px;
}
</style>
