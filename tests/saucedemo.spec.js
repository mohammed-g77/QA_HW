const { test, expect } = require('@playwright/test');

test('saucedemo', async ({ page }) => {
 
  await page.goto('https://www.saucedemo.com/');


  await page.waitForTimeout(1000);
  await page.locator('[data-test="username"]').fill( 'standard_user');
 await page.waitForTimeout(1000);
   await page.locator('[data-test="password"]').fill( 'secret_sauce');
 await page.waitForTimeout(1000);
  await page.click('[data-test="login-button"]');
  
 
await expect(page).toHaveURL('https://www.saucedemo.com/inventory.html');

  await page.click('button[data-test="add-to-cart-sauce-labs-backpack"]');
 
  await page.click('.shopping_cart_link');

  
  const cartItem = await page.locator('.inventory_item_name');
  await expect(cartItem).toHaveText('Sauce Labs Backpack');

  
  await page.getByRole('button', { name: 'Open Menu' }).click();  
  await page.locator('[data-test="logout-sidebar-link"]').click();  
 
  await expect(page).toHaveURL('https://www.saucedemo.com/');
});
