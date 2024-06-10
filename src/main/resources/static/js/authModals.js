     function openLoginModal() {
            document.getElementById('loginModal').classList.remove('hidden');
        }

        function closeLoginModal() {
            document.getElementById('loginModal').classList.add('hidden');
        }

        function openSignupModal() {
            document.getElementById('signupModal').classList.remove('hidden');
        }

        function closeSignupModal() {
            document.getElementById('signupModal').classList.add('hidden');
        }

         function openLoginModalSpecial() {
            document.getElementById('signupModal').classList.add('hidden');
            document.getElementById('loginModal').classList.remove('hidden');
        }