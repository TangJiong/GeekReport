import Vue from 'vue'
import randomColor from 'randomColor'
import _ from 'lodash'

// function onError (response) {
//   return {
//     status: response.status,
//     message: response.statusText
//   }
// }

var DatasourceService = {
  create (datasource) {
    return Vue.http.post('datasource', datasource).then(response => response.data)
  },

  update (datasource) {
    return Vue.http.patch('datasource', datasource).then(response => response.data)
  },

  getByProject (projectId) {
    return Vue.http.get('datasource?project_id=' + projectId).then(response => response.data)
  },

  getById (id) {
    return Vue.http.get('datasource/' + id).then(response => response.data)
  },

  delete (id) {
    return Vue.http.delete('datasource/' + id).then(response => response.data)
  }
}

var ProjectService = {
  create (project) {
    return Vue.http.post('project', project).then(response => response.data)
  },

  update (project) {
    return Vue.http.patch('project', project).then(response => response.data)
  },

  getByUser (userId) {
    return Vue.http.get('project?user_id=' + userId).then(response => response.data)
  },

  getById (id) {
    return Vue.http.get('project/' + id).then(response => response.data)
  },

  delete (id) {
    return Vue.http.delete('project/' + id).then(response => response.data)
  }
}

var ParagraphService = {
  create (paragraph) {
    return Vue.http.post('paragraph', paragraph).then(response => response.data)
  },

  update (paragraph) {
    return Vue.http.patch('paragraph', paragraph).then(response => response.data)
  },

  getByProject (projectId) {
    return Vue.http.get('paragraph?project_id=' + projectId).then(response => response.data)
  },

  getById (id) {
    return Vue.http.get('paragraph/' + id).then(response => response.data)
  },

  delete (id) {
    return Vue.http.delete('paragraph/' + id).then(response => response.data)
  }
}

var QueryService = {
  run (query) {
    return Vue.http.post('query/run', query).then(response => response.data)
  },

  save (query) {
    return Vue.http.post('query/save', query).then(response => response.data)
  },

  getByParagraph (paraId) {
    return Vue.http.get('query?paragraph_id=' + paraId).then(response => response.data)
  },

  delete (id) {
    return Vue.http.delete('query/' + id).then(response => response.data)
  },

  setMaxAge (config) {
    return Vue.http.patch('query/maxage', config).then(response => response.data)
  }
}

var VisualizationService = {

  /**
   * visualization builder
   * @param  {Array} data   result rows, eg: [{name: 'apple', price: 100}, {name: 'orange', price: 120}]
   * @param  {Object} config {groupColumn: '', observeColumn: '', measureColumn: ''}
   * @return {Object} config object for Chart.js
   */
  build (data, config) {
    // 1.group by groupColumn
    let groupedData = _.groupBy(data, config.group_column)
    let datasets = []
    let labels = []
    // 2.measureColumn -> data
    _.forOwn(groupedData, (value, key) => {
      let dataset = {}
      dataset.fill = true
      if (config.chart_type === 'pie' || config.chart_type === 'polarArea') {
        dataset.borderColor = value.map(row => randomColor(
          {
            seed: row[config.measure_column],
            format: 'rgba',
            alpha: 0.6
          }))
      } else {
        dataset.borderColor = randomColor({seed: key, format: 'rgba'})
      }

      if (config.chart_type === 'pie' || config.chart_type === 'polarArea') {
        dataset.backgroundColor = value.map(row => randomColor(
          {
            seed: row[config.measure_column],
            format: 'rgba',
            alpha: 0.6
          }))
      } else {
        dataset.backgroundColor = randomColor({seed: key, format: 'rgba', alpha: 0.2})
      }
      dataset.pointBorderColor = randomColor({seed: key, format: 'rgba'})
      dataset.pointBackgroundColor = randomColor({seed: key, format: 'rgba', alpha: 0.2})
      dataset.label = key
      dataset.data = value.map(row => row[config.measure_column])
      datasets.push(dataset)
      let _labels = value.map(row => row[config.observe_column])
      if (_labels.length > labels.length) {
        labels = _labels
      }
    })
    return {
      type: config.chart_type,
      data: {
        labels: labels,
        datasets: datasets
      },
      options: {}
    }
  },

  create (visualization) {
    return Vue.http.post('visualization', visualization).then(response => response.data)
  },

  update (visualization) {
    return Vue.http.patch('visualization', visualization).then(response => response.data)
  },

  getByQuery (queryId) {
    return Vue.http.get('visualization?query_id=' + queryId).then(response => response.data)
  },

  getById (id) {
    return Vue.http.get('visualization/' + id).then(response => response.data)
  },

  delete (id) {
    return Vue.http.delete('visualization/' + id).then(response => response.data)
  }
}

export {
  DatasourceService,
  ProjectService,
  ParagraphService,
  QueryService,
  VisualizationService
}
