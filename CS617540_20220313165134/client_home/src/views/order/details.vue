<template>
  <div class="page_order" id="order_details">
    <div class="warp">
      <div class="container">
        <div class="row">
          <div class="col">
            <div class="card_order">
              <!-- 订单列表 -->
              <div v-if="$check_action('/order/details', 'get')">
                <div class="state">
                  {{ state }}
                </div>
                <div class="contact_info">
                  <div class="line_1">
                    <b-icon class="icon_address" icon="geo-alt"></b-icon>
                    <span class="name">{{ contact_name }}</span>
                    <span class="phone"> {{ contact_phone }}</span>
                  </div>
                  <span class="line_2">{{ contact_address }}</span>
                </div>
                <div class="goods_list" v-for="(obj, idx) in list" :key="idx">
                  <div class="item_goods flex_sbc">
                    <div class="img_block">
                      <img
                        style="width: 4.5rem; height: 4.5rem"
                        :src="$fullUrl(obj.img)"
                        mode="scaleToFill"
                      />
                    </div>
                    <div class="middle_info">
                      <div class="title ellipsis_2">
                        {{ obj.title }}
                      </div>
                      <div class="priceAndNum">
                        <span class="price"> ￥{{ obj.price }} </span>
                        <span class="num"> ×{{ obj.num }} </span>
                      </div>
                    </div>
                  </div>
                  <div class="num_info"></div>
                </div>
                <div class="sum_price flex_sbc">
                  <span>需付款</span>
                  <span style="color: var(--color_red); font-weight: 600"
                    >￥{{ sum_price }}</span
                  >
                </div>
                <div class="btn_block">
                  <div v-if="$check_action('/order/details', 'set')">
                    <div
                      class="btn_trade"
                      v-if="state === '待付款'"
                      @click="goto_pay()"
                    >
                      去支付
                    </div>
                    <!--<div-->
                      <!--v-if="state === '已签收' || state === '待发货'"-->
                      <!--class="btn_trade"-->
                      <!--@click="refund()">-->
                      <!--申请退款-->
                    <!--</div>-->
                    <!--<div-->
                      <!--v-if="state === '待退款'"-->
                      <!--class="btn_trade"-->
                      <!--@click="cancel_refund()">-->
                      <!--取消退款申请-->
                    <!--</div>-->
                  </div>
                </div>
                <div class="bottom_order">
                  <div class="order_num flex_sbc">
                    <span>订单编号：</span><span>{{ order_number }}</span>
                  </div>
                  <div class="time flex_sbc">
                    <span>下单时间：</span><span>{{ create_time | formatDate }}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import mixin from "@/mixins/page.js";

export default {
  mixins: [mixin],
  data() {
    return {
      // 登录权限
      oauth: {
        signIn: true,
        user_group: [],
      },
      url_get_list: "~/api/order/get_list",
      field: "order_number",
      query: {
        order_number: "",
      },
      contact_name: "",
      contact_phone: "",
      contact_address: "",
      order_number: "",
      create_time: "",
      state: "",
    };
  },
  methods: {
    /**
     * 获取订单后
     * @param {Object} json 响应对象
     */
    get_list_after(json) {
      var {
        contact_name,
        contact_phone,
        contact_address,
        order_number,
        create_time,
        state,
      } = json.result.list[0];
      this.contact_name = contact_name;
      this.contact_phone = contact_phone;
      this.contact_address = contact_address;
      this.order_number = order_number;
      this.create_time = create_time;
      this.state = state;
      return json;
    },
    /**
     * 退款
     */
    // refund() {
    //   this.$post(
    //     "~/api/order/set?order_number=" + this.order_number,
    //     {
    //       state: "待退款",
    //     },
    //     (res) => {
    //       this.state = "待退款";
    //     }
    //   );
    // },
    /**
     * 取消退款
     */
    // cancel_refund(){
    //     this.$post(
    //     "~/api/order/set?order_number=" + this.order_number,
    //     {
    //       state: "待发货",
    //     },
    //     (res) => {
    //       this.state = "待发货";
    //     }
    //   );
    // },
    /**
     * 支付
     */
    goto_pay() {
      this.$router.push(
        "/pay/detail?" +
          "order_number=" +
          this.order_number +
          "&&" +
          "sum_price=" +
          this.sum_price
      );
    },
  },
  computed: {
    /**
     * 计算总价
     */
    sum_price() {
      var sum_price = 0;
      this.list.map((o) => (sum_price += o.price * o.num));
      return sum_price;
    },
  },
};
</script>

<style scoped>
.ellipsis_2 {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
.page_order {
  min-height: 100vh;
  background-color: #f8f8f8;
}

.flex_sbc {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.contact_info {
  background-color: #fff;
  padding: 0.3rem 1rem;
  margin-bottom: 0.5rem;
}

.contact_info .line_1 {
  margin-bottom: 0.2rem;
}

.contact_info .line_2 {
  font-size: 0.9rem;
}

.contact_info .name {
  padding-right: 1rem;
  font-weight: 600;
}

.contact_info .phone {
  color: var(--color_grey);
}

.state {
  padding: 0.3rem 1rem;
  background-color: #fff;
  color: var(--color_primary);
  border-bottom: 1px solid #f8f8f8;
  font-size: 1.1rem;
}

.goods_list {
  padding: 0.2rem 0.8rem;
  background-color: #fff;
}

.goods_list .item_goods {
  align-items: stretch;
  border-bottom: 1px solid #f4f4f4;
}

.goods_list .img_block {
  padding: 0.2rem;
}

.goods_list .middle_info {
  width: calc(100% - 5.5rem);
}

.goods_list .title {
  font-size: 0.9rem;
  min-height: 1.4rem;
}

.goods_list .num_info {
  display: flex;
  justify-content: space-between;
}

.goods_list .price,
.goods_list .num {
  color: var(--color_grey);
  font-size: 0.5rem;
}

.sum_price {
  height: 2.5rem;
  background-color: #ffffff;
  padding: 0 0.8rem;
  border-bottom: 1px solid #f4f4f4;
}
.btn_block {
  padding: 0.8rem 2rem;
  background-color: #fff;
  border-bottom: 1px solid #f4f4f4;
  display: flex;
  justify-content: center;
  align-items: center;
}
.btn_trade {
  text-align: center;
  padding: 0.5rem 2rem;
  border: 1px solid var(--color_grey);
  border-radius: 4rem;
  cursor: pointer;
  width: 256px;
}
.btn_trade:hover {
  opacity: 0.7;
}
.bottom_order {
  background-color: #fff;
  font-size: 0.8rem;
  color: var(--color_grey);
}
.bottom_order > div {
  padding: 0.6rem;
}
.bottom_order > div + div {
  border-top: 1px solid #f4f4f4;
}
.order_num {
  margin-bottom: 0.3rem;
}
</style>
