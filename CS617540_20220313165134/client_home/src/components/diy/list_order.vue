<template>
  <div class="list_order">
    <div class="item_order" v-for="(o, i) in list" :key="i">
      <div class="state">
        {{ o.state }}
      </div>
      <div class="goods_list" v-for="(obj, idx) in o.list" :key="idx">
        <router-link
          :to="'./details?order_number=' + o.order_number"
          class="item_goods flex_sbc"
        >
          <div class="img_block">
            <img
              style="width: 4.5rem; height: 4.5rem"
              :src="$fullUrl(obj[vm.img])"
              mode="scaleToFill"
            />
          </div>
          <div class="middle_info">
            <div class="title">
              {{ obj[vm.title] }}
            </div>
            <div class="description">
              {{ obj[vm.description] }}
            </div>
          </div>
          <div class="right_info">
            <div class="price">￥{{ obj[vm.price] }}</div>
            <div class="num">×{{ obj[vm.num] }}</div>
          </div>
        </router-link>
      </div>
      <div class="sum_price flex_sbc">
        <span>实付：￥{{ o.sum_price }}</span>
        <b-button
          @click="del_order(o.order_number, i)"
          class="btn_del"
          type="default"
          size="mini"
        >
          <b-icon icon="trash"></b-icon>
        </b-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    list: {
      type: Array,
      default: function () {
        return [];
      },
    },
    vm: {
      type: Object,
      default: function () {
        return {
          order_number: "order_number",
          title: "title",
          img: "img",
          price: "price",
          price_ago: "price_ago",
          price_count: "price_count",
          num: "num",
          goods_id: "goods_id",
          description: "description",
          contact_name: "contact_name",
          contact_phone: "contact_phone",
          contact_address: "contact_address",
          postal_code: "postal_code",
          user_id: "user_id",
        };
      },
    },
  },
  data() {
    return {};
  },
  methods: {
    /**
     * 删除订单
     * @param { Number } order_number 订单编号
     */
    del_order(order_number, i) {
      this.$get(
        "~/api/order/del",
        {
          order_number,
        },
        (res) => {
          this.list.splice(i, 1);
        }
      );
    }
  }
};
</script>

<style scoped>
.flex_cc {
  display: flex;
  justify-content: center;
  align-items: center;
}

.flex_sbc {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.item_order {
  background-color: #ffffff;
  padding: 0.4rem 0.8rem;
  margin-bottom: 0.8rem;
}
.item_order .state {
  text-align: right;
  color: var(--color_primary);
  border-bottom: 2px solid #f8f8f8;
  padding-bottom: 0.5rem;
}

.goods_list .item_goods {
  align-items: stretch;
  padding: 0.2rem;
  border-bottom: 1px solid #f4f4f4;
}

.goods_list .img_block {
  padding: 0.2rem;
}

.goods_list .middle_info {
  width: calc(100% - 8rem);
}

.goods_list .title {
  font-size: 0.9rem;
  min-height: 1.4rem;
}

.goods_list .description {
  color: var(--color_grey);
  font-size: 0.6rem;
}

.goods_list .right_info {
  display: flex;
  flex-direction: column;
}

.goods_list .price {
  font-size: 0.9rem;
}

.goods_list .num {
  color: var(--color_grey);
  text-align: right;
  font-size: 0.5rem;
}

.sum_price {
  height: 2.5rem;
}

.sum_price > text {
  color: var(--color_red);
}

.btn_del {
  padding: 0 1rem;
  margin: 0;
}
.btn_del:hover {
  background: var(--color_info_b);
  border-color: var(--color_info_b);
}
</style>
