import Vue from 'vue'

const profile = Vue.resource('/message{/id}')

export default {
    get: id => profile.get({id}),
    ChangeSubscription: channelId => Vue.http.post(`/profile/change-subscription/${channelId}`)
}