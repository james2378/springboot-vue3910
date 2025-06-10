<template>
  <div class="page_order" id="order_list">
    <div class="warp" v-if="$check_action('/order/list', 'get')">
      <div class="container">
        <div class="row">
          <div class="col-10">
            <div class="card_goods_list">
              <h4>我的订单</h4>
              <div class="filter_block">
                <!-- 排序 -->
                <div
                  class="bar_sort"
                  :class="{ active: query.orderby == '`price` desc' }"
                >
                  <b-dropdown text="排序" variant="outline-dark" left>
                    <b-dropdown-item @click="sort_order_desc()">
                      价格从高到低
                    </b-dropdown-item>
                    <b-dropdown-item @click="sort_order_asc()">
                      价格从低到高
                    </b-dropdown-item>
                    <b-dropdown-item
                      v-for="(o, i) in sort_list"
                      :key="i"
                      @click="sort_order(o)"
                    >
                      {{ o.name }}
                    </b-dropdown-item>
                  </b-dropdown>
                </div>
                <!-- /排序 -->
                <!-- 订单状态筛选 -->
                <div
                  class="bar_sort"
                  :class="{ active: query.orderby == '`price` desc' }"
                >
                  <b-dropdown text="订单状态" variant="outline-dark" left>
                    <b-dropdown-item
                      v-for="(o, i) in state_list"
                      :key="i"
                      @click="state_filter(o)"
                    >
                      {{ o }}
                    </b-dropdown-item>
                  </b-dropdown>
                </div>
                <!-- /订单状态筛选 -->
              </div>
              <list_order :list="list_diff"></list_order>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import list_order from "@/components/diy/list_order.vue";
import mixin from "@/mixins/page.js";

export default {
  mixins: [mixin],
  components: {
    list_order,
  },
  data() {
    return {
      // 登录权限
      oauth: {
        signIn: true,
        user_group: [],
      },
      url_get_list: "~/api/order/get_list",
      list_diff: [],
      query: {
        user_id: this.$store.state.user.user_id,
      },
      list_order_type: [],
      sort_list: [
        {
          name: "更新时间从高到低",
          value: "`create_time` desc",
        },
        {
          name: "更新时间从低到高",
          value: "`create_time` asc",
        },
      ],
      state_list: [
        "全部",
        "待付款",
        "已付款",
      ],
    };
  },
  methods: {
    /**
     *下拉排序选择
     */
    sort_order(o) {
      this.query.orderby = o.value;
      this.search();
    },
    /**
     *订单状态筛选
     */
    state_filter(o) {
      if (o == "全部") {
        this.query.state = "";
      } else {
        this.query.state = o;
      }
      this.get_list();
    },
    /**
     * 从低到高排序
     */
    compare_asc(property) {
      return function (a, b) {
        var value1 = a[property];
        var value2 = b[property];
        return value1 - value2;
      };
    },
    /**
     * 从高到低排序
     */
    compare_desc(property) {
      return function (a, b) {
        var value1 = a[property];
        var value2 = b[property];
        return value2 - value1;
      };
    },
    /**
     * 订单从低到高排序
     */
    sort_order_asc() {
      this.list_diff.sort(this.compare_asc("sum_price"));
    },
    /**
     * 订单从高到低排序
     */
    sort_order_desc() {
      this.list_diff.sort(this.compare_desc("sum_price"));
    },
    /**
     *获取订单前
     @param { Object } param 用户user_id
     */
    get_list_before(param) {
      param.user_id = this.$store.state.user.user_id;
      return param;
      for (let index = 0; index < array.length; index++) {
        const element = array[index];
      }
    },
    /**
     * 获订单后
     * @param {Object} json 响应对象
     * @return { Object } 订单对象
     */
    get_list_after(json) {
      // 根据订单号区分列表
      var list_diff = [];
      var order_num = "";
      var index = -1;
      json.result.list.map((o) => {
        if (o.order_number === order_num) {
          list_diff[index]["list"].push(o);
          list_diff[index]["sum_price"] += o.price_count;
        } else {
          index += 1;
          order_num = o.order_number;
          list_diff[index] = {};
          var obj_diff = list_diff[index];
          obj_diff["create_time"] = o.create_time;
          obj_diff["order_number"] = order_num;
          obj_diff["list"] = [];
          obj_diff["list"].push(o);
          obj_diff["sum_price"] = o.price_count;
          obj_diff["state"] = o.state;
        }
      });
      this.list_diff = list_diff;
      return json;
    },
  },
};
</script>

<style scoped>
.page_order {
  background-color: #f8f8f8;
  min-height: 100vh;
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
