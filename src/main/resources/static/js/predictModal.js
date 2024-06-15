// document.addEventListener('DOMContentLoaded', function() {
     function openModal(action, event) {
        event.preventDefault();

        const form = event.target.closest('form');
        const securityId = form.querySelector('.securityId').value;
        const securityName = form.querySelector('.securityName').value;
        const marketId = form.querySelector('.marketId').value;

        // Set the values in the predictionModal
        document.getElementById('modalSecurityId').value = securityId;
        document.getElementById('modalSecurityName').value = securityName;
        document.getElementById('modalSecurity').textContent = securityName ? "Security: " + securityName : "Market: Poll";
        document.getElementById('modalMarketId').value = marketId;
        document.getElementById('action').value = action;

        // Show the modal and overlay
        document.getElementById('predictionModal').classList.remove('hidden');
        document.getElementById('modalOverlay').classList.remove('hidden');
    }

    function closeModal() {
        document.getElementById('predictionModal').classList.add('hidden');
        document.getElementById('modalOverlay').classList.add('hidden');
    }

       function closePredictModal() {

            document.getElementById('predictionModal').classList.add('hidden');
            document.getElementById('modalOverlay').classList.add('hidden');
        }
//})