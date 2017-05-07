<template>
  <div class="visual-wrapper">
    <visual-table v-if="type === 'table'"></visual-table>
    <visual-chart v-else :config="chartConfig"></visual-chart>
  </div>
</template>

<script>
import VisualChart from './VisualChart'
import VisualTable from './VisualTable'

export default {
  components: {
    VisualChart,
    VisualTable
  },

  props: {
    type: {
      type: String,
      default: 'table'
    }
  },

  data () {
    return {
      chartConfig: {
        type: 'bar',
        data: {
          labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
          datasets: [{
            label: '# of Votes',
            data: [12, 19, 3, 5, 2, 3],
            backgroundColor: [
              'rgba(255, 99, 132, 0.5)',
              'rgba(54, 162, 235, 0.5)',
              'rgba(255, 206, 86, 0.5)',
              'rgba(75, 192, 192, 0.5)',
              'rgba(153, 102, 255, 0.5)',
              'rgba(255, 159, 64, 0.5)'
            ],
            borderColor: [
              'rgba(255,99,132,1)',
              'rgba(54, 162, 235, 1)',
              'rgba(255, 206, 86, 1)',
              'rgba(75, 192, 192, 1)',
              'rgba(153, 102, 255, 1)',
              'rgba(255, 159, 64, 1)'
            ],
            borderWidth: 1
          }]
        },
        options: {
          scales: {
            yAxes: [{
              ticks: {
                beginAtZero: true
              }
            }]
          }
        }
      }
    }
  },

  mounted () {

  },

  methods: {

  }
}
</script>

<style scoped>

</style>
