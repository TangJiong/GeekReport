<template>
  <el-table
    :data="data.rows"
    height="360"
    border
    style="width: 100%">
    <el-table-column
      v-for="column in data.columns"
      :key="column"
      :prop="column"
      :label="column">
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  props: {
    data: {
      type: Object,
      default: () => {
        return {
          rows: [],
          columns: []
        }
      }
    }
  },
  data () {
    return {

    }
  }
}
</script>
