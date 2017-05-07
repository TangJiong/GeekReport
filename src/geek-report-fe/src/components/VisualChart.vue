<template>
  <div class="chart-wrapper">
    <canvas></canvas>
  </div>
</template>

<script>
import Chart from 'chart.js'

Chart.defaults.global.maintainAspectRatio = false

export default {
  props: {
    config: {
      type: Object,
      required: true
    }
  },

  data () {
    return {
      chart: null
    }
  },

  mounted () {
    this.drawChart()
  },

  beforeDestroy () {
    this.destroyChart()
  },

  methods: {
    drawChart () {
      var ctx = this.$el.firstChild
      this.chart = new Chart(ctx, this.config)
    },

    updateChart () {
      if (this.chart !== null) {
        this.chart.update()
      }
    },

    destroyChart () {
      if (this.chart !== null) {
        this.chart.destroy()
      }
    }
  },

  watch: {
    'config': 'updateChart'
  }
}
</script>

<style scoped>
.chart-wrapper {
  height: 300px;
}
</style>
