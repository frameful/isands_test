<script setup lang="ts">

import { DialogTrigger } from "~/components/ui/dialog";
import { Button } from "~/components/ui/button";
import NewEntryDialogue from "~/components/table/NewEntryDialogue.vue";
import { Icon } from "@iconify/vue";

interface ShopState {
  shops: any[];
  page: number;
  pageSize: number;
}

const shopState = reactive<ShopState>({
  shops: [],
  page: 0,
  pageSize: 10,
})

function createUpdateDto(shop: any) {
  return {
    id: shop.id,
    name: shop.name,
    address: shop.address,
  }
}

async function fetchShops(page: number)  {
  shopState.shops = await $fetch("http://localhost:8081/estore/api/shop/?page="
      + shopState.page + "&pageSize="
      + shopState.pageSize) as any;
}

watch(() => shopState.page,
    (page) => {
      fetchShops(page);
    }
)

onMounted(async () => {
  await fetchShops(0);
})

const keyFilterSet = {
  name: {
    regex: new RegExp("[а-яА-Яa-z]{1,150}"),
    format: "Строка до 150 символов",
    name: 'Название'
  },
  address: {
    regex: new RegExp("[а-яА-Яa-z]+"),
    format: "Строка",
    name: 'Адрес'
  },
}


</script>

<template>
  <Dialog>
    <DialogTrigger as-child>
        <Button variant="outline">
          Создать магазин
        </Button>
    </DialogTrigger>
    <NewEntryDialogue @newEntry="fetchShops(shopState.page)"
                      name="shop" :keySet="keyFilterSet"
                      endpoint="/estore/api/shop" />
  </Dialog>
  <div>
    Страница: {{ shopState.page + 1 }}

    <Button @click="shopState.page--" variant="outline" :disabled="shopState.page == 0">Предыдущая страница</Button>
    -
    <Button @click="shopState.page++" variant="outline"
            :disabled="shopState.shops.length !== shopState.pageSize">
      Следующая страница
    </Button>
  </div>
  <Table>
    <TableHeader>
      <TableRow>
        <TableHead class="w-[100px]">
          id
        </TableHead>
        <TableHead>name</TableHead>
        <TableHead>address</TableHead>
      </TableRow>
    </TableHeader>
    <TableBody>
      <TableRow v-for="shop in shopState.shops" :key="shop.id">
        <TableCell class="font-medium">
          {{ shop.id }}
        </TableCell>
        <TableCell>{{ shop.name }}</TableCell>
        <TableCell>{{ shop.address }}</TableCell>
        <TableCell>
          <Dialog>
            <DialogTrigger>
              <Button variant="outline">
                <Icon icon="radix-icons:pencil-1" />
              </Button>
            </DialogTrigger>
            <NewEntryDialogue editing
                              :item-to-edit="createUpdateDto(shop)"
                              @newEntry="fetchShops(shopState.page)"
                              name="shop"
                              :keySet="keyFilterSet"
                              :endpoint='"/estore/api/shop/" + shop.id' />
          </Dialog>
        </TableCell>
      </TableRow>
    </TableBody>
  </Table>

</template>