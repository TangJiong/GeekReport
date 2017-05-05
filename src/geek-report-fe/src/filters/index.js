import moment from 'moment'
import Vue from 'vue'

moment.locale('zh-cn')

export default function install () {
  Vue.filter('toReadableTime', function (value) {
    let time = moment(value)
    return time.calendar()
  })
}
