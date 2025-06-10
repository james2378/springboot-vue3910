<template>
  <div class="page_cart" id="cart_index">
    <div class="warp">
      <div class="container">
        <div class="row">
          <div class="col">
            <div class="card">
              <h4>我的购物车</h4>
              <list_cart :list="list"></list_cart>
            </div>
            <button @click="del()">删除</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import mixin from "@/mixins/page.js";
import list_cart from "@/components/diy/list_cart.vue";
export default {
  mixins: [mixin],
  components: {
    list_cart,
  },
  data() {
    return {
      url_get_list: "~/api/cart/get_list?",
      list: [],
      query: {
        page: 1,
        size: 10
      },
      list_cart_type: [],
    };
  },
  methods: {
    get_list_before(param) {
      param.user_id = this.user.user_id
      return param
    },
    /**
     * 获取评论后
     * @param {Object} json 响应对象
     */
    get_list_after(json) {
      return json.result.list.map((o) => {
        o.selected = false;
      });
    },
    del() {
      // this.$axios.delete("http://cs.chengduziyi.com/api/cart/del", {
      //   headers: {
      //     'x-auth-token': this.$store.state.user.token
      //   },
      //   params: {
      //     cart_id: 88
      //   }
      // });
      // $.ajax({
      //   type : "DELETE", //请求方式
      //   async: false, // fasle表示同步请求，true表示异步请求
      //   contentType: "application/json;charset=UTF-8", //请求的媒体类型
      //   url : "http://cs.chengduziyi.com/api/cart/del",//请求地址
      //   headers: {
      //     'x-auth-token': this.$store.state.user.token
      //   },
      //   data : {
      //     cart_id: 90
      //   }, //数据，json字符串
      //   success : function(result) { //请求成功
      //     console.log(result);
      //   },
      //   error : function(e){  //请求失败，包含具体的错误信息
      //     console.log(e.status);
      //     console.log(e.responseText);
      //   }
      // });
      for (let i=0;i<this.list.length;i++){
        if (this.list[i].selected) {
          this.$get(`~/api/cart/del?cart_id=${this.list[i].cart_id}`, {}, (res) => {
            this.list.splice(i, 1);
            i--;
          });
        }
      }
    },
  }
}
</script>

<style scoped>
.page_cart {
  max-width: 1200px;
  margin: 0 auto;
}
.container {
  min-height: 800px;
}
.card {
  padding: 5px;
}
.filter_block {
  display: flex;
  flex-direction: row;
  justify-content: start;
  margin-bottom: 10px;
}
.bar_filter {
  display: flex;
  align-items: center;
}
li {
  list-style: none;
}
.bar_sort {
  margin-left: 5px;
}
</style>

