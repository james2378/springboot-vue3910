<template>
  <div class="page_user" id="user_address_edit">
    <div class="warp">
      <div class="container">
        <div class="row">
          <div class="col-12">
            <div class="card_menu">
              <!-- 添加新地址 -->
              <form_address :data="obj"></form_address>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import form_address from "@/components/diy/form_address.vue";
import mixin from "@/mixins/page.js";

export default {
  mixins: [mixin],
  components: {
    form_address,
  },
  data() {
    return {
      // 登录权限
      oauth: {
        signIn: true,
        user_group: [],
      },
      url_get_obj: "~/api/address/get_obj?",
      field: "address_id",
      query: {
        address_id: 0,
      },
      obj: {},
    };
  },
  onLoad() {
    this.obj.user_id = this.user.user_id;
  },
};
</script>

<style scoped>
</style>
