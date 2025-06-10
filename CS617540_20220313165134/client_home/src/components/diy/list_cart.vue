<template>
  <div class="cart_block">
    <div class="list_cart">
      <div class="item_cart" v-for="(o, i) in list" :key="i">
        <!-- 选择按钮 -->
        <div
          class="choose_btn"
          :class="{ active: o[vm.selected] }"
          @click="select_func(o,i)"
        >
          <div class="btn_circle">
            <span class="icon_wrap">
              <b-icon v-if="o[vm.selected]" icon="check"></b-icon>
            </span>
          </div>
        </div>
        <div class="cart_body">
          <div class="img_block">
            <img :src="$fullUrl(o[vm.img])" />
          </div>

          <div class="right_info">
            <div class="title ellipsis_2">
              {{ o[vm.title] }}
            </div>
            <!-- 删除商品 -->
             <!-- v-if="$check_action('/cart/list', 'del')" -->
            <div
              class="btn_del"
              @click="del_cart(o, i)"
            >
              <b-icon
                type="trash"
                class="icon_del"
                color="var(--color_grey)"
                icon="trash"
              ></b-icon>
            </div>
            <!-- v-if="$check_action('/cart/details', 'set')" -->
            <div class="bottom_price">
              <div class="price">
                <span>￥{{ o[vm.price] }}</span>
                <span class="price_ago">￥{{ o[vm.price_ago] }}</span>
              </div>
              <div
                class="handle_num"
              >
                <div class="subtract" @click="sub_num(o, i)">
                  <span>-</span>
                </div>
                <div class="num">
                  {{ o[vm.num] }}
                </div>
                <div class="add" @click="add_num(o)">
                  <span>+</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 全选 -->
    <div class="bar_settle" v-if="$check_action('/order/list', 'add')">
      <div
        class="choose_btn"
        :class="{ active: select_all_flag }"
        @click="select_all()"
      >
        <div class="btn_circle">
          <b-icon
            style="color: #fff; font-size: 0.3rem"
            v-if="select_all_flag"
            icon="check"
          ></b-icon>
        </div>
        <span>全选</span>
      </div>
      <div class="right_settle">
        <div class="sum_block">
          <span>共计：</span>
          <span class="sum">￥{{ count() }}</span>
        </div>
        <div class="settle">
          <button
            class="btn_settle"
            color="#fff"
            @click="settle_all()"
            size="mini"
          >
            购买
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import  axios from "axios"
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
          cart_id: "cart_id",
          title: "title",
          img: "img",
          user_id: "user_id",
          state: "state",
          price: "price",
          price_ago: "price_ago",
          price_count: "price_count",
          num: "num",
          goods_id: "goods_id",
          description: "description",
          selected: "selected",
        };
      },
    },
  },
  data() {
    return {
      sum_price: 0,
      select_all_flag:false
    };
  },
  computed: {
  },
  methods: {
    count() {
      var sum_price = 0;
      this.list.map((o) => {
        if (o.selected) {
          sum_price = parseFloat(sum_price) + parseFloat(o.price_count);
        }
      });
      return sum_price;
    },
    selectedAll() {
      return this.list.every((o) => o.selected);
    },
    /**
     * 增加数量
     * @param {Object} obj 商品对象
     */
    add_num(obj) {
      var { cart_id, num, price, price_count } = obj;
      num = parseInt(num) + 1;
      price_count = parseFloat(price_count) + parseFloat(price);
      this.$post(
        `~/api/cart/set?cart_id=${cart_id}`,
        {
          num,
          price,
          price_count,
        },
        (res) => {
          this.list.map((o) => {
            if (o.cart_id === cart_id) {
              (o.num = num), (o.price_count = price_count);
            }
          });
        }
      );
    },
    /**
     * 减少数量
     * @param { Object } obj 商品对象
     * @param { Object} i 规定删除项目的位置
     */
    sub_num(obj, i) {
      var { cart_id, num, price, price_count } = obj;
      num = parseInt(num) - 1;
      price_count = parseFloat(price_count) - parseFloat(price);
      if (num === 0) {
        this.$get(`~/api/cart/del?cart_id=${cart_id}`, {}, (res) => {
          this.list.splice(i, 1);
        });
      } else {
        this.$post(
          `~/api/cart/set?cart_id=${cart_id}`,
          {
            num,
            price,
            price_count,
          },
          (res) => {
            this.list.map((o) => {
              if (o.cart_id === cart_id) {
                o.num = num;
                o.price_count = price_count;
              }
            });
          }
        );
      }
    },
    /**
     * 删除购物车
     * @param { Object } obj 商品对象
     * @param { Number} i 规定删除项目的位置
     */
    del_cart(o, i) {
      this.$get(`~/api/cart/del?cart_id=${o.cart_id}`, {}, (res) => {
        this.list.splice(i, 1);
      });
    },
    /**
     * 选择
     * @param { Object } o 选择对象
     */
    select_func(o,i) {
      o.selected = !o.selected;
      this.$set(this.list,i,o);
      this.select_all_flag =this.selectedAll();
    },
    /**
     * 全选
     */
    select_all() {
      if (this.selectedAll()) {
        this.list.map((o) => {
          o.selected = false;
        });
        this.select_all_flag = false;
      } else {
        this.list.map((o) => {
          o.selected = true;
        });
        this.select_all_flag = true;
      }
    },
    /**
     * 根据当前时间和随机数生成流水号
     */
    order_code() {
      var orderCode = "";
      for (
        var i = 0;
        i < 6;
        i++ //6位随机数，用以加在时间戳后面。
      ) {
        orderCode += Math.floor(Math.random() * 10);
      }
      orderCode = new Date().getTime() + orderCode; //时间戳，用来生成订单号。
      return orderCode;
    },
    /**
     * 异步单个结算
     * @param { Object } obj 结算对象
     * @param { Number } index 规定删除项目的位置
     * @param { Object } order_number 订单编号
     */
    async settle_one(obj, index, order_number,address) {
      // 获取被选中的商品
      var {
        title,
        img,
        price,
        price_ago,
        price_count,
        num,
        type,
        goods_id,
        description,
        user_id,
        cart_id,
      } = obj;
      // 请求参数
      var body = {
        order_number,
        title,
        img,
        price,
        price_ago,
        price_count,
        num,
        type,
        goods_id,
        description,
        "contact_name":address.obj.name,
        "contact_phone":address.obj.phone,
        "contact_address":address.obj.address,
        "postal_code":address.obj.postcode,
        user_id,
      };
      console.log(body)
      // 延迟发送添加订单请求
      await new Promise((resolve, reject) => {
        this.$post("~/api/order/add?", body, (res) => {
          if (res.result) {
            resolve();
          }
        });
      });
      //删除购物车
      await new Promise((resolve, reject) => {
        this.$get(
          "~/api/cart/del?",
          {
            cart_id,
          },
          (res) => {
            if (res.result) {
              this.list.splice(index, 1);
              resolve();
            }
          }
        );
      });
    },
    /**
     * 选中的结算
     */
    async settle_all() {
      // 查询库存
      let flag = false;
      for (let i=0;i<this.list.length;i++){
        if (this.list[i].selected) {
          let goods_json = await this.$get("~/api/goods/get_obj?", {"goods_id": this.list[i].goods_id});
            if (goods_json.result && goods_json.result.obj) {
              if (this.list[i].num>goods_json.result.obj.inventory){
                flag = true
                this.$toast("库存不足");
                break;
              }
            }
        }
      }
      if (flag){
        return;
      }
      //生成订单号
      var order_number = this.order_code();
      // 获取联系方式
      this.$get(
              "~/api/address/get_obj?",
              {
                user_id:this.$store.state.user.user_id,
                default: 1,
              },
              (res) => {
                if (res.result) {
                  console.log(res.result);
                  // 选出选中的物品
                  var list_settle = this.list.map(async (o, i) => {
                    if (o.selected) {
                      await this.settle_one(o, i, order_number,res.result);
                      this.$router.push("/order/details?order_number=" + order_number);
                    }
                  });
                  this.$toast("加入订单成功");
                } else {
                  this.$toast("地址未添加");
                  return;
                }
              }
      );
    },
    /**
     * 判断是否在微信小程序
     * @return { Boolean }
     */
    is_wx_env() {
      // 判断是否在微信小程序中打开
      var ua = window.navigator.userAgent.toLowerCase();
      let that = this;
      var bool = false;
      if (ua.match(/MicroMessenger/i) == "micromessenger") {
        //判断是否是微信环境
        //微信环境下
      }
      return bool;
    },
  },
};
</script>

<style scoped>
.ellipsis_2 {
  text-overflow: -o-ellipsis-lastline;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  line-clamp: 2;
  -webkit-box-orient: vertical;
}
.cart_block {
  min-height: 580px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.list_cart {
  margin: 5px 5px;
  background-color: #fff;
  border-radius: 1rem;
  margin-bottom: 5rem;
}

.list_cart .item_cart {
  display: flex;
  justify-content: start;
  align-items: center;
  padding: 0.6rem;
}

.list_cart .item_cart + .item_cart {
  border-top: 1px solid #eee;
}

.list_cart .choose_btn {
  display: flex;
  justify-content: center;
  align-items: center;
}

.list_cart .choose_btn .circle {
  width: 1rem;
  height: 1rem;
  border-radius: 50%;
}

.list_cart .choose_btn .btn_circle {
  display: inline-block;
  width: 0.9rem;
  height: 0.9rem;
  margin-right: 0.5rem;
  border-radius: 50%;
  border: 1px solid var(--color_grey);
  cursor: pointer;
}
.list_cart .choose_btn .btn_circle .icon_wrap {
  color: #fff;
  position: relative;
  top: -1px;
}
.list_cart .choose_btn.active {
  color: var(--color_primary);
}

.list_cart .choose_btn.active .btn_circle {
  background-color: var(--color_primary);
  border: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

.list_cart .cart_body {
  display: flex;
  justify-content: space-between;
  align-items: stretch;
  width: 90%;
}
.list_cart .cart_body .img_block {
  width: 200px;
  min-height: 100px;
  max-height: 200px;
}
.list_cart .cart_body .img_block img {
  width: 100%;
  height: 100%;
}
.list_cart .right_info {
  width: calc(100% - 4.5rem);
  padding-left: 0.6rem;
  position: relative;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
}

.list_cart .title {
  font-size: 0.6rem;
  width: 85%;
  margin-top: 15px;
}

.list_cart .btn_del {
  position: absolute;
  top: 0;
  right: 0;
  width: 2.2rem;
  height: 2.2rem;
  line-height: 2.2rem;
  text-align: center;
  cursor: pointer;
  border: 1px solid var(--color_default);
  border-radius: 5px;
  font-size: 18px;
  background: var(--color_default);
}
.icon_del {
  color: white;
}
.list_cart .btn_del:hover {
  background: var(--color_primary_b);
  border-color: var(--color_primary_b);
}
.list_cart .bottom_price {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.list_cart .price {
  color: var(--color_red);
  font-weight: bold;
  font-size: 1rem;
}

.list_cart .price_ago {
  margin-left: 0.2em;
  text-decoration: line-through;
  font-size: 0.7rem;
  color: #999;
}

.list_cart .handle_num {
  display: flex;
  justify-content: space-between;
  align-items: center;
  cursor: pointer;
}

.list_cart .handle_num > div {
  height: 25px;
  width: 25px;
  text-align: center;
  margin-left: 10px;
}

.list_cart .subtract,
.add {
  border: 1px solid #eee;
  border-radius: 50%;
  color: #919191;
  background-color: #eee;
}

.list_cart .handle_num span {
  position: relative;
  top: -1px;
}

.list_cart .handle_num .num {
  width: 1.4rem;
}

.bar_settle {
  width: 100%;
  height: 2.5rem;
  background-color: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 0.8rem;
  border-top: 1px solid #eee;
}

.bar_settle .choose_btn {
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}

.bar_settle .choose_btn .circle {
  width: 1rem;
  height: 1rem;
  border-radius: 50%;
}

.bar_settle .choose_btn .btn_circle {
  display: inline-block;
  width: 0.9rem;
  height: 0.9rem;
  margin-right: 0.5rem;
  border-radius: 50%;
  border: 1px solid var(--color_grey);
}

.bar_settle .choose_btn.active {
  color: var(--color_primary);
}

.bar_settle .choose_btn.active .btn_circle {
  background-color: var(--color_primary);
  border: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

.bar_settle .right_settle {
  display: flex;
  justify-content: space-between;
}

.bar_settle .sum_block {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-right: 0.5rem;
}

.bar_settle .sum {
  color: var(--color_red);
}

.bar_settle .settle {
  display: flex;
  align-items: center;
}

.bar_settle .btn_settle {
  color: #fff;
  border-radius: 5rem;
  background-color: var(--color_red);
}

.bar_settle .button-hover {
  opacity: 0.8;
}
</style>
